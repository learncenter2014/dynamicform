package bl.beans;

import org.mongodb.morphia.annotations.Entity;

@Entity(value="template")
public class TemplateBean extends Bean {

  String label;
  String path;


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

  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * @param path
   *          the path to set
   */
  public void setPath(String path) {
    this.path = path;
  }

}
