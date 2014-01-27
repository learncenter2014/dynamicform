package actions;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * This class is used to generate the html element according to the incoming attributes.
 *
 * Created by wangronghua on 14-1-27.
 */
public class ElementGenAction extends ActionSupport {

  private String elementId;
  private String elementType;
  private String label;
  private String name;
  private String value;
  private String result;

  public String get() {

    return SUCCESS;
  }
}
