package core.componentext;

/**
 * Created by wangronghua on 14-2-14.
 */
public class NameValueBean {
  private String name;


  private String value;

  public NameValueBean(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
