package actions;

import bl.constants.DBConstants;
import bl.mongobus.DataBusiness;
import com.opensymphony.xwork2.ActionContext;
import core.Constants;
import core.TemplateHelper;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangronghua on 14-2-9.
 */
public class DataPageInputAction extends BaseAction {
    private String tableName;
    private String recordId;
    private String result;

    public String input() {
        if(StringUtils.isEmpty(tableName) || StringUtils.isEmpty(recordId)) {
            return ERROR;
        }
        Map data = DataBusiness.get().get(tableName, recordId);
        String viewId = (String) data.get(DBConstants.DB_FIELD_VIEWID);
        if(null != viewId)
        {
            data.put("recordId", recordId);
            data.put("tableName", tableName);
            data.put("rootPath", getRootPath());
            result = TemplateHelper.get().getTemplate(viewId, data);
        }
        return SUCCESS;
    }
//
//
//  public String loadPage() {
//    HttpServletRequest request = ServletActionContext.getRequest();
//    this.dataId = request.getParameter("dataId");
//    this.pageName = request.getParameter("pageName");
//    PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
//    PageBean record = (PageBean)pab.getLeafByName(pageName).getResponseData();
//    if(record == null) return ERROR;
//
//    StringBuilder rb = new StringBuilder();
//    if(record != null) {
//      List<TemplateBean> tList = record.getTemplateList();
//      for(TemplateBean tBean : tList){
//        rb.append("\n").append(this.getHtmlStringByTemplate(tBean));
//      }
//    }
//      result = rb.toString();
//      return SUCCESS;
//  }

    public String save() {
//    this.dataId = request.getParameter("dataId");
//    this.templateId = request.getParameter("templateId");
//    //DataBusiness dataBusiness = (DataBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DATABUSINESS);
        Map<String, Object> paraMap = ActionContext.getContext().getParameters();
        Map record = DataBusiness.get().get(tableName, recordId);
        if(null != record) {
          DataBusiness.get().update(tableName, recordId, processMap(record, paraMap));
        } else {
          DataBusiness.get().insert(tableName, processMap(record, paraMap));
        }

        // todo save user data to database
        return SUCCESS;
    }

    private Map processMap(Map sourceMap, Map paraMap) {
        Iterator<Map.Entry> it = paraMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = it.next();
            Object value = entry.getValue();
            if(value instanceof String[] ) {
                sourceMap.put(entry.getKey(), StringUtils.join((String[]) value, Constants.PROPERTY_SPLIT));
            } else {
                sourceMap.put(entry.getKey(), value);
            }
        }
        return sourceMap;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

}
