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
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bl.beans.DepartmentBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.DepartmentBusiness;
import bl.mongobus.MongoCommonBusiness;
import dao.MongoDBConnectionFactory;

/**
 * 部门Excel上传
 * 
 * @author zlj Date: 2014/07/08
 * @version 1.0
 *
 */
public class DepartmentUploadExcelAction extends UploadExcelAction {
	
	private static final long serialVersionUID = 1L;
	private static Logger LOG = LoggerFactory.getLogger(DepartmentUploadExcelAction.class);
	private static DepartmentBusiness deb = (DepartmentBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DEPARTMENTBUSINESS);

	private int counters;
	
	@Override
	public String prepareExcel() {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(this.myFile);
			Workbook wb = new HSSFWorkbook(fin);
            Sheet sheet = wb.getSheetAt(0);
            // Decide which rows to process
            int rowStart = 0;
            int rowEnd = Math.min(5000, sheet.getLastRowNum());

            String[][] departmentMappingExcel = {
                    {"序号", "sequence"},
                    {"科室编码", "uniqueDepartmentId"},
                    {"科室名称", "deptName"},
                    {"随访角色", "fuRole"},
                    {"外线前缀", "outsideThePrefix"},
                    {"拨打长途加拨号码", "longDistanceThePrefix"},
                    {"是否有长途", "hasLongDistance"},
            };
            int[] departmentMappingExcelColumns = new int[departmentMappingExcel.length];
            int foundHeaderRow = 0;
            ArrayList<DepartmentBean> deptBeanArrayList = new ArrayList<DepartmentBean>();
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
                                for (int i = 0; i < departmentMappingExcel.length; i++) {
                                    if (cellValue.equals(departmentMappingExcel[i][0])) {
                                    	departmentMappingExcelColumns[i] = cell.getColumnIndex();
                                        foundHeaderRow = row.getRowNum();
                                    }
                                }
                            }
                        }
                    }
                } else if (foundHeaderRow != -1) {
                    //表头信息处理完毕
                    String uniqueDepartmentId = cellConvert(row.getCell(departmentMappingExcelColumns[1], Row.RETURN_BLANK_AS_NULL));
                    //实体名称必须存在
                    if (uniqueDepartmentId == null || uniqueDepartmentId.isEmpty()) {
                        continue;
                    }
                    DepartmentBean deptFromDB = (DepartmentBean) deb.getLeafByUniqueCode(uniqueDepartmentId).getResponseData();
                    DepartmentBean deptBean = null;

                    // 更新的场合
                    if (deptFromDB != null) {
                    	deptBean = deptFromDB;
                    }
                    // 新增的场合
                    else {
                    	deptBean = new DepartmentBean();
                        String deptUid = ObjectId.get().toString();
                        deptBean.setId(deptUid);
                    }

                    for (int i = 0; i < departmentMappingExcel.length; i++) {
                        String cellValue = cellConvert(row.getCell(departmentMappingExcelColumns[i], Row.RETURN_BLANK_AS_NULL));
                        if (!departmentMappingExcel[i][1].equals("hasLongDistance")) {
                            try {
                                BeanUtils.setProperty(deptBean, departmentMappingExcel[i][1], cellValue);
                            } catch (Exception ex) {
                                LOG.warn("reflected issue {}", ex.getMessage());
                            }
                        }
                    }
                    deptBeanArrayList.add(deptBean);
                }
            }
            //批量保存
            this.counters = deptBeanArrayList.size();
            deb.batchUpdateDeptList(deptBeanArrayList);
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
