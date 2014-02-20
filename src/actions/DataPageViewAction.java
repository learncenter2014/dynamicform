package actions;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by wangronghua on 14-2-9.
 */
public class DataPageViewAction extends ActionSupport {

  String templateId;
  String targetId;
  String userId;

  public String loadDataByTarget() {
    // todo load records by target and template when clicking on the target
    return SUCCESS;
  }

  public String loadTargetByUser() {
    // todo load targets and show when clicking into the template menu
    return SUCCESS;
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
