package core.componentext;

import dynamicschema.Radio;

/**
 * Created by wangronghua on 14-2-14.
 */
public class RadioExtension extends Radio {
  private NameValueBean[] options;

  public void init() {
    options = new ListValueParser(this.getListvalue()).parse();
  }

  public NameValueBean[] getOptions() {
    return options;
  }
}
