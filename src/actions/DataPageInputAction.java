package actions;

import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.DataBusiness;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import core.TemplateGenerator;
import core.TemplateHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangronghua on 14-2-9.
 */
public class DataPageInputAction extends ActionSupport {
  private String userData;
  private String templateId;
  private String targetId;
  private String userId;

  private String result;

  public String input() {
    String templateId = "dynamicform";
    TemplateGenerator g = new TemplateGenerator();
    g.genTemplate("/Users/wangronghua/workspace/DynamicForm/testresources/dynamicform.xml", "dynamicform.ftl");
    Map map = DataBusiness.get().get(templateId);
    result = TemplateHelper.getTemplate("dynamicform", map);
//    try {
//      PrintWriter out = ServletActionContext.getResponse().getWriter();
//      out.write(result);
//    } catch (IOException e) {
//      e.printStackTrace();
//      // todo handle exception
//    }
    return SUCCESS;
  }

  public String save() {
    String templateId = "dynamicform";
    //DataBusiness dataBusiness = (DataBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DATABUSINESS);
    Map<String, Object> paraMap = ActionContext.getContext().getParameters();
    DataBusiness.get().insert(templateId, processMap(paraMap));
    // todo save user data to database
    return SUCCESS;
  }

  private Map processMap(Map paraMap) {
    Map result = new HashMap();
    Iterator<Map.Entry> it = paraMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = it.next();
      Object value = entry.getValue();
      if(value instanceof String[]) {
        String val = StringUtils.join((String[])value, ',');
        result.put(entry.getKey(), val);
      }
    }
    return result;
  }

  public String getUserData() {
    return userData;
  }

  public void setUserData(String userData) {
    this.userData = userData;
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

}
