package bl.beans;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "page")
public class PageBean extends Bean {

  String label;

  @Reference(ignoreMissing = true, lazy = true)
  List<TemplateBean> templateList = null;

  /**
   * @return the templateList
   */
  public List<TemplateBean> getTemplateList() {
    return templateList;
  }

  /**
   * @param templateList
   *          the templateList to set
   */
  public void setTemplateList(List<TemplateBean> templateList) {
    this.templateList = templateList;
  }

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @param label
   *          the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

}
