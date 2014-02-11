package actions;

import com.opensymphony.xwork2.ActionSupport;
import core.TemplateGenerator;
import core.TemplateHelper;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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
    TemplateGenerator g = new TemplateGenerator();
    g.genTemplate("/Users/wangronghua/workspace/DynamicForm/testresources/dynamicform.xml", "dynamicform.ftl");
    result = TemplateHelper.getTemplate("dynamicform", new HashMap());
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
    // todo save user data to database
    return SUCCESS;
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
