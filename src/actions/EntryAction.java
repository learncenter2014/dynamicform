package actions;

import bl.beans.DocumentBean;
import bl.beans.EntryBean;
import bl.beans.EntryCodeBean;
import bl.beans.UnitBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.DocumentBusiness;
import bl.mongobus.EntryBusiness;
import bl.mongobus.UnitBusiness;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.ServletActionContext;
import vo.table.TableDataVo;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by peter on 14-6-21.
 */
public class EntryAction extends BaseTableAction<EntryBusiness> {

    private EntryBean entry;
    private String documentId;
    private List<UnitBean> unitBeanList;
    private static final UnitBusiness ub = (UnitBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_UNIT_BUSINESS);
    private static final DocumentBusiness dbs = (DocumentBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DOCUMENTBUSINESS);

    public List<UnitBean> getUnitBeanList() {
        return unitBeanList;
    }

    public void setUnitBeanList(List<UnitBean> unitBeanList) {
        this.unitBeanList = unitBeanList;
    }

    @Override
    public String getCustomJsp() {
        return "/pages/entry/entryPost.jsp";
    };

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/entry";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("sequence", "序号").disableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "实体名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("code", "实体编码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("englishName", "实体英文名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("elementType", "元素类型").addSearchOptions(new String[][]{{"0", "1"}, {"定性", "定量"}}).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("dataType", "数据类型").addSearchOptions(new String[][]{{"0", "1", "2", "3"}, {"字符型", "整数型", "浮点型", "日期型"}}).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("subElementType", "元素归类").addSearchOptions(new String[][]{{"0", "1", "2", "3", "4", "5"}, {"主元素", "子元数", "伪参考值主元素", "伪检查值主元素", "伪单位主元素", "伪主元素"}}).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("htmlType", "表单元素").addSearchOptions(new String[][]{{"0", "1", "2", "3", "4", "5", "6"}, {"标签", "文本框", "文本域", "下拉框", "复选框", "单选框", "日期"}}).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("defaultValue", "实体缺省值").setHiddenColumn(true));
        init.getAoColumns().add(new TableHeaderVo("standardEntry", "标准分类").addSearchOptions(new String[][]{{"0", "1", "2"}, {"CDISC", "机构标准", "非标准"}}).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("pseudoReferenceUnitCode", "标准单位"));
        init.getAoColumns().add(new TableHeaderVo("minValue", "最小值").setHiddenColumn(true));
        init.getAoColumns().add(new TableHeaderVo("maxValue", "最大值").setHiddenColumn(true));
        init.getAoColumns().add(new TableHeaderVo("maxLength", "字段长度").setHiddenColumn(true));
        init.getAoColumns().add(new TableHeaderVo("precision", "小数位数").setHiddenColumn(true));
        init.getAoColumns().add(new TableHeaderVo("size", "字段显示长度").setHiddenColumn(true));
        init.getAoColumns().add(new TableHeaderVo("regularExpression", "正则表达式").setHiddenColumn(true));
        init.getAoColumns().add(new TableHeaderVo("pseudoReferenceLowerValue", "参考值下限").setHiddenColumn(true));
        init.getAoColumns().add(new TableHeaderVo("pseudoReferenceUpperValue", "参考值上限").setHiddenColumn(true));
        init.getAoColumns().add(new TableHeaderVo("description", "注解").setHiddenColumn(true));

        return init;
    }

    public String exportTable() {
        getModel().setIDisplayStart(0);
        getModel().setIDisplayLength(10000);
        long count = getBusiness().getCount(getModel());
        TableDataVo tableData = getBusiness().query(getModel());

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/msexcel;charset=UTF-8");  //两种方法都可以
        String fileName = this.getTableId()+".xls";
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        //客户端不缓存
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        TableInitVo tiv = getTableInit();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("导出" + sdf.format(new Date()));
            List<TableHeaderVo> tableHeader = tiv.getAoColumns();
            Row row = sheet.createRow(0);
            int columnLength = 0;
            for (int i = 0; i < tableHeader.size(); i++) {
                row.createCell(columnLength).setCellValue(tableHeader.get(i).getsTitle());
                columnLength++;
            }
            //additional code.
            row.createCell(columnLength).setCellValue("元素代码");

            int index = 1;
            for (Object vo : tableData.getAaData()) {
                row = sheet.createRow(index);
                int columnLengthData = 0;
                for (int i = 0; i < tableHeader.size(); i++) {
                    TableHeaderVo thv = tableHeader.get(i);
                    //可读性的属性才可以反射
                    if (PropertyUtils.isReadable(vo, thv.getmData()) && PropertyUtils.getProperty(vo, thv.getmData()) != null) {
                        row.createCell(columnLengthData).setCellValue(PropertyUtils.getProperty(vo, thv.getmData()).toString());
                    } else {
                        row.createCell(columnLengthData).setCellValue("");
                    }
                    columnLengthData++;
                }
                List<EntryCodeBean> listEntryCode = ((EntryBean)vo).getEntryCodeBeanList();
                StringBuilder sbder = new StringBuilder();
                for (int j = 0; j < listEntryCode.size(); j++) {
                    sbder.append(listEntryCode.get(j).getValue() + ":" + listEntryCode.get(j).getName());
                    if (j < listEntryCode.size() - 1) {
                        sbder.append(",");
                    }
                }
                row.createCell(columnLengthData).setCellValue(sbder.toString());
                index++;
            }
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (Exception e) {
            LOG.error("this exception [{}]", e.getMessage());
        }
        return null;
    }

    @Override
    public String save() throws Exception {
        BusinessResult result = null;
        if (StringUtils.isEmpty(entry.getId())) {
            result = getBusiness().createLeaf(entry);
        } else {
            result = getBusiness().updateLeaf(entry, entry);
        }
        if (result != null && result.getErrors().size() > 0) {
            for (Object error : result.getErrors()) {
                addActionError(error.toString());
            }
            return INPUT;
        }
        if (result != null && result.getMessages().size() > 0) {
            for (Object message : result.getMessages()) {
                addActionMessage(message.toString());
            }
            return SUCCESS;
        }
        return SUCCESS;
    }

    public String edit() {
        entry = (EntryBean) getBusiness().getLeaf(getId()).getResponseData();
        unitBeanList = (List<UnitBean>) ub.getAllLeaves().getResponseData();
        UnitBean ubTemp = new UnitBean();
        ubTemp.setName("");
        ubTemp.setCode("");
        unitBeanList.add(0,ubTemp);
        return SUCCESS;
    }

    @Override
    public String delete() throws Exception {
        if (getIds() != null) {
            for (String id : getIds()) {
                getBusiness().deleteLeaf(id);
            }
        }
        return SUCCESS;
    }

    @Override
    public String add() {
        entry = new EntryBean();
        entry.setDocumentId(this.documentId);
        unitBeanList = (List<UnitBean>) ub.getAllLeaves().getResponseData();
        UnitBean ubTemp = new UnitBean();
        ubTemp.setName("");
        ubTemp.setCode("");
        unitBeanList.add(0,ubTemp);
        return SUCCESS;
    }
    @Override
    public String getTableTitle() {
        String prefixPath = getRequest().getContextPath()+"/";
        DocumentBean db = (DocumentBean) dbs.getLeaf(this.documentId).getResponseData();
        return "<ul class=\"breadcrumb\"><li>随访设计</li><li><a href=\""+prefixPath+"document/index.action\">系统模块[<i style='color:#58c9f3'>"+db.getName()+"</i>]</a></li><li class=\"active\"><a href=\""+prefixPath+"entry/index.action?documentId="+this.documentId+"\">实体</a></li></ul>";
    }
    @Override
    public TableQueryVo getModel() {
        TableQueryVo model = super.getModel();
        model.getFilter().put("documentId", this.documentId + "");
        model.getSort().put("sequence","asc");
        return model;
    }

    @Override
    public String getAddButtonParameter(){
        return "documentId="+this.documentId;
    }

    public EntryBean getEntry() {
        return entry;
    }

    public void setEntry(EntryBean entry) {
        this.entry = entry;
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
