package actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import bl.beans.TemplateBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.FormBusiness;

import com.opensymphony.xwork2.ActionSupport;

import core.componentext.NameValueBean;

/**
 * Created by wangronghua on 14-2-9.
 */
public class DataPageViewAction extends ActionSupport {

  String templateId;
  String patientId;
  String userId;

  List<NameValueBean> patients;

  List<TemplateBean> templates;

  public String loadDataByTarget() {
    // todo load records by target and template when clicking on the target
    return SUCCESS;
  }

  public String loadTargetByUser() {
    // todo load targets and show when clicking into the template menu
    return SUCCESS;
  }

  public String loadPaients() {
    patients = new ArrayList<NameValueBean>();
    NameValueBean bean1 = new NameValueBean("Patient_Test1", "PatientTest1");
    NameValueBean bean2 = new NameValueBean("Patient_Test2", "PatientTest2");
    patients.add(bean1);
    patients.add(bean2);
    return SUCCESS;
  }

  public String loadTemplates() {
    HttpServletRequest request = ServletActionContext.getRequest();
    patientId = request.getParameter("patientId");
    FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
    // find all records in the template;
    BusinessResult br = fb.getAllLeaves();
    templates = (List<TemplateBean>) br.getResponseData();
    if(templates == null) {
      templates = new ArrayList<>();
    }
    return SUCCESS;
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


  public List<NameValueBean> getPatients() {
    return patients;
  }

  public List<TemplateBean> getTemplates() {
    return templates;
  }
}
