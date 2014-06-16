package core.componentext;

import dynamicschema.Document;

/**
 * Created by wangronghua on 14-6-16.
 */
public class DocumentExtension extends Document{
  public String getInnerHTML() {
    return innerHTML;
  }

  public void setInnerHTML(String innerHTML) {
    this.innerHTML = innerHTML;
  }

  private String innerHTML;
}
