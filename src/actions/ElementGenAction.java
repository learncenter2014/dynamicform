package actions;

import com.opensymphony.xwork2.ActionSupport;
import core.TemplateHelper;

import java.util.HashMap;
import java.util.Map;

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


  public String getHtmlForInput() {
    Map paraMap = new HashMap();
    paraMap.put("elementId", elementId);
    paraMap.put("label", label);
    paraMap.put("name", name);
    result = TemplateHelper.getTemplate("text", paraMap);
    return SUCCESS;
  }

  public void setElementId(String elementId) {
    this.elementId = elementId;
  }

  public String getElementType() {
    return elementType;
  }

  public void setElementType(String elementType) {
    this.elementType = elementType;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
