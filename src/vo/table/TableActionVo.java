/**
 * 
 */
package vo.table;

/**
 * Table Action
 * 
 * @author gudong
 * @since $Date:2014-02-16$
 */
public class TableActionVo {

  private String actionName;
  private String msg = "";
  private boolean ajax = true;
  private String html;
  public TableActionVo(String actionName, String html) {
    super();
    this.actionName = actionName;
    this.html = html;
  }
  public TableActionVo(String actionName, String html, String msg) {
    super();
    this.actionName = actionName;
    this.html = html;
    this.msg = msg;
  }

  public String getActionName() {
    return actionName;
  }

  public void setActionName(String actionName) {
    this.actionName = actionName;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String toString() {
    return "<span actionName='" + actionName + "' title='" + actionName + "'>" + html + "</span>";
  }
  public boolean isAjax() {
    return ajax;
  }
  public void setAjax(boolean ajax) {
    this.ajax = ajax;
  }
  public TableActionVo disableAjax(){
    this.ajax  =false;
    return this;
  }
}
