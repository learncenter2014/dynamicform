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
  private String html;

  public TableActionVo(String actionName, String html) {
    super();
    this.actionName = actionName;
    this.html = html;
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

  public String toString() {
    return "<span actionName='" + actionName + "' title='" + actionName + "'>" + html + "</span>";
  }
}
