package core.componentext;

import dynamicschema.SelectEntry;

/**
 * Created by wangronghua on 14-2-14.
 */
public class SelectExtension extends SelectEntry {

  private NameValueBean[] options;

  public void init() {
    options = new ListValueParser(this.getListvalue()).parse();
  }

  public NameValueBean[] getOptions() {
    return options;
  }
}
