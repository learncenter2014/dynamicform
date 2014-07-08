package actions;

import bl.beans.EntryBean;
import bl.beans.EntryCodeBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.EntryBusiness;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pli on 14-7-3.
 */
public class EntryUploadExcelAction extends UploadExcelAction {
    private static Logger LOG = LoggerFactory.getLogger(EntryUploadExcelAction.class);
    private static EntryBusiness enb = (EntryBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_ENTRYBUSINESS);

    private String documentId;
    private int counters;

    public int getCounters() {
        return counters;
    }

    public void setCounters(int counters) {
        this.counters = counters;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

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

            String[][] entryMappingExcel = {
                    {"序号", "sequence"},
                    {"实体编码", "code"},
                    {"实体名称", "name"},
                    {"实体英文名称", "englishName"},
                    {"元素类型", "elementType"},
                    {"数据类型", "dataType"},
                    {"元素归类", "subElementType"},
                    {"表单元素", "htmlType"},
                    {"实体缺省值", "defaultValue"},
                    {"标准分类", "standardEntry"},
                    {"最小值", "minValue"},
                    {"最大值", "maxValue"},
                    {"字段长度", "maxLength"},
                    {"小数位数", "precision"},
                    {"字段显示长度", "size"},
                    {"正则表达式", "regularExpression"},
                    {"参考值下限", "pseudoReferenceLowerValue"},
                    {"参考值上限", "pseudoReferenceUpperValue"},
                    {"注解", "description"},
                    {"元素代码", "entryCode"}
            };
            int[] entryMappingExcelColumns = new int[entryMappingExcel.length];
            int foundHeaderRow = 0;
            ArrayList<EntryBean> entryBeanArrayList = new ArrayList<EntryBean>();
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
                                for (int i = 0; i < entryMappingExcel.length; i++) {
                                    if (cellValue.equals(entryMappingExcel[i][0])) {
                                        entryMappingExcelColumns[i] = cell.getColumnIndex();
                                        foundHeaderRow = row.getRowNum();
                                    }
                                }
                            }
                        }
                    }
                } else if (foundHeaderRow != -1) {
                    //表头信息处理完毕
                    String entryName = cellConvert(row.getCell(entryMappingExcelColumns[2], Row.RETURN_BLANK_AS_NULL));
                    //实体名称必须存在
                    if (entryName == null && entryName.isEmpty()) {
                        continue;
                    }
                    EntryBean entryFromDB = (EntryBean) enb.getLeafByName(entryName).getResponseData();
                    EntryBean entryBean = null;

                    if (entryFromDB != null) {
                        entryBean = entryFromDB;
                    } else {
                        entryBean = new EntryBean();
                        String entryUid = ObjectId.get().toString();
                        //全部新增
                        entryBean.setId(entryUid);
                    }

                    //关联引用
                    entryBean.setDocumentId(this.documentId);
                    //加到更新列表
                    entryBeanArrayList.add(entryBean);
                    for (int i = 0; i < entryMappingExcel.length; i++) {
                        String cellValue = cellConvert(row.getCell(entryMappingExcelColumns[i], Row.RETURN_BLANK_AS_NULL));
                        if (!entryMappingExcel[i][1].equals("entryCode")) {
                            try {
                                BeanUtils.setProperty(entryBean, entryMappingExcel[i][1], cellValue);
                            } catch (Exception ex) {
                                LOG.warn("reflected issue {}", ex.getMessage());
                            }
                        } else if(!cellValue.isEmpty()){
                            String[] splitEntryCode = cellValue.split(";");
                            ArrayList<EntryCodeBean> entryCodeBeans = new ArrayList<EntryCodeBean>();
                            for (int j = 0; j < splitEntryCode.length; j++) {
                                String[] equalSplit = splitEntryCode[j].split(":");
                                if(equalSplit.length==2){
                                    EntryCodeBean entryCodeBean = new EntryCodeBean();
                                    entryCodeBean.setName(equalSplit[1]);
                                    entryCodeBean.setValue(equalSplit[0]);
                                    entryCodeBean.setId(ObjectId.get().toString());
                                    entryCodeBean.setEntryId(entryBean.getId());
                                    entryCodeBeans.add(entryCodeBean);
                                }
                            }
                            entryBean.setEntryCodeBeanList(entryCodeBeans);
                        }
                    }
                }
            }
            //批量保存
            this.counters = entryBeanArrayList.size();
            enb.batchUpdateEntryList(this.documentId,entryBeanArrayList);
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
}
