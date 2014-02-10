package actions;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by wangronghua on 14-2-9.
 */
public class DataPageInputAction extends ActionSupport {
  private String userData;
  private String templateId;
  private String targetId;
  private String userId;

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

}
