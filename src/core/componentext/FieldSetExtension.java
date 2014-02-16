package core.componentext;

import dynamicschema.FieldSet;

/**
 * Created by wangronghua on 14-2-15.
 */
public class FieldSetExtension extends FieldSet {

  public String getInnerHTML() {
    return innerHTML;
  }

  public void setInnerHTML(String innerHTML) {
    this.innerHTML = innerHTML;
  }

  private String innerHTML;

}
