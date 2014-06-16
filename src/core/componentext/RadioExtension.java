package core.componentext;

import dynamicschema.RadioEntry;

/**
 * Created by wangronghua on 14-2-14.
 */
public class RadioExtension extends RadioEntry {
  private NameValueBean[] options;

  public void init() {
    options = new ListValueParser(this.getListvalue()).parse();
  }

  public NameValueBean[] getOptions() {
    return options;
  }
}
