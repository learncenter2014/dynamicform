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

  private String method;
  private String text;
  private String msg; // user ${fieldName} instead of value
  private boolean multiRows = true;

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isMultiRows() {
    return multiRows;
  }

  public void setMultiRows(boolean multiRows) {
    this.multiRows = multiRows;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
