package actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bl.beans.DiseaseBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.DiseaseBusiness;

/**
 * 单病种Excel上传
 * 
 * @author zlj Date: 2014/07/08
 * @version 1.0
 *
 */
public class DiseaseUploadExcelAction extends UploadExcelAction {
	
	private static final long serialVersionUID = 1L;
	private static Logger LOG = LoggerFactory.getLogger(DiseaseUploadExcelAction.class);
	private static DiseaseBusiness dib = (DiseaseBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DISEASEBUSINESS);

	private int counters;
	
	@Override
	public String prepareExcel() {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(this.myFile);
			Workbook wb = new HSSFWorkbook(fin);
            Sheet sheet = wb.getSheetAt(0);
            int rowStart = 0;
            int rowEnd = Math.min(5000, sheet.getLastRowNum());

            String[][] diseaseMappingExcel = {
                    {"序号", "sequence"},
                    {"病种所属系统", "system"},
                    {"疾病名称", "name"},
                    {"ICD-10", "ICD10"},
                    {"手术名称", "operationName"},
                    {"ICD-9-CM-3", "ICD9CM3"},
                    {"备注(病例数）", "content"},
            };
            int[] diseaseMappingExcelColumns = new int[diseaseMappingExcel.length];
            int foundHeaderRow = 0;
            ArrayList<DiseaseBean> diseaseBeanArrayList = new ArrayList<DiseaseBean>();
            for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                int lastColumn = row.getLastCellNum();

                if (foundHeaderRow == 0) {
                    //简单的探查表头信息
                    for (int cn = 0; cn < lastColumn; cn++) {
                    	Cell cell = row.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                        if (cell == null) {
                            LOG.warn("空白单元格" + row.getRowNum());
                        } else {
                            String cellValue = cellConvert(cell);
                            if (cellValue != null && !cellValue.isEmpty()) {
                                for (int i = 0; i < diseaseMappingExcel.length; i++) {
                                    if (cellValue.equals(diseaseMappingExcel[i][0])) {
                                    	diseaseMappingExcelColumns[i] = cell.getColumnIndex();
                                        foundHeaderRow = row.getRowNum();
                                    }
                                }
                            }
                        }
                    }
                } else if (foundHeaderRow != -1) {
                    //表头信息处理完毕
                    String uniqueName = cellConvert(row.getCell(diseaseMappingExcelColumns[2], Row.RETURN_BLANK_AS_NULL));
                    //实体名称必须存在
                    if (uniqueName == null || uniqueName.isEmpty()) {
                        continue;
                    }
                    DiseaseBean diseaseFromDB = (DiseaseBean) dib.getLeafByName(uniqueName).getResponseData();
                    DiseaseBean diseaseBean = null;

                    // 更新的场合
                    if (diseaseFromDB != null) {
                    	diseaseBean = diseaseFromDB;
                    }
                    // 新增的场合
                    else {
                    	diseaseBean = new DiseaseBean();
                        String diseaseUid = ObjectId.get().toString();
                        diseaseBean.setId(diseaseUid);
                    }

                    for (int i = 0; i < diseaseMappingExcel.length; i++) {
                        String cellValue = cellConvert(row.getCell(diseaseMappingExcelColumns[i], Row.RETURN_BLANK_AS_NULL));
                        if (!diseaseMappingExcel[i][1].equals("content")) {
                            try {
                                BeanUtils.setProperty(diseaseBean, diseaseMappingExcel[i][1], cellValue);
                            } catch (Exception ex) {
                                LOG.warn("reflected issue {}", ex.getMessage());
                            }
                        }
                    }
                    diseaseBeanArrayList.add(diseaseBean);
                }
            }
            //批量保存
            this.counters = diseaseBeanArrayList.size();
            dib.batchUpdateDiseaseList(diseaseBeanArrayList);
		} catch (Exception e) {
            LOG.error("解析Excel遇到异常 {}", e.getMessage());
        } finally {
            try {
                fin.close();
            } catch (IOException e) {
                LOG.error("关闭文件流遇到异常 {}", e.getMessage());
            }
        }
		return SUCCESS;
	}

	public int getCounters() {
		return counters;
	}

	public void setCounters(int counters) {
		this.counters = counters;
	}
}
