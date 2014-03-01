package core.componentext;

import dynamicschema.Row;

/**
 * Created by wangronghua on 14-3-1.
 */
public class RowExtension extends Row {
  public String getInnerHTML() {
    return innerHTML;
  }

  public void setInnerHTML(String innerHTML) {
    this.innerHTML = innerHTML;
  }

  private String innerHTML;
}
