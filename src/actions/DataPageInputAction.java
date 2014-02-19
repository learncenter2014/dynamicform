package actions;

import bl.beans.TemplateBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.DataBusiness;
import bl.mongobus.FormBusiness;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import core.Constants;
import core.TemplateGenerator;
import core.TemplateHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangronghua on 14-2-9.
 */
public class DataPageInputAction extends ActionSupport implements ServletContextAware {

  private ServletContext servletContext = null;
  private String userData;
  private String templateId;

  private String patientId;
  private String userId;

  private String result;

  public String input() {
    HttpServletRequest request = ServletActionContext.getRequest();
    this.patientId = request.getParameter("patientId");
    this.templateId = request.getParameter("templateId");
    if(templateId == null) return ERROR;

    FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
    BusinessResult br = fb.getLeafByName(templateId);
    TemplateBean temp = (TemplateBean)br.getResponseData();
    if(temp == null) return ERROR;
    String fullPath = this.servletContext.getRealPath(temp.getPath());

    TemplateGenerator g = new TemplateGenerator();
    g.genTemplate(fullPath, templateId + ".ftl");

    Map map = DataBusiness.get().get(templateId, patientId, userId);
    if(map == null) {
      map = new HashMap();
      map.put("patientId", patientId);
      map.put("templateId", templateId);
    }
    result = TemplateHelper.getTemplate(templateId, map);

    return SUCCESS;
  }

  public String save() {
    HttpServletRequest request = ServletActionContext.getRequest();
    this.patientId = request.getParameter("patientId");
    this.templateId = request.getParameter("templateId");
    //DataBusiness dataBusiness = (DataBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DATABUSINESS);
    Map<String, Object> paraMap = ActionContext.getContext().getParameters();
    Map record = DataBusiness.get().get(templateId, patientId, userId);
    if(null != record) {
      DataBusiness.get().update(templateId, patientId, processMap(paraMap));
    } else {
      DataBusiness.get().insert(templateId, processMap(paraMap));
    }

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
      } else {
        result.put(entry.getKey(), entry.getValue());
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPatientId() {
    return patientId;
  }

  public void setPatientId(String patientId) {
    this.patientId = patientId;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  /**
   * aware injection of struts2
   */
  @Override
  public void setServletContext(ServletContext context) {
    this.servletContext = context;
  }

}
