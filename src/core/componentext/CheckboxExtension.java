package core.componentext;

import dynamicschema.Checkbox;

/**
 * Created by wangronghua on 14-2-14.
 */
public class CheckboxExtension extends Checkbox {

  private NameValueBean[] options;

  public void init() {
    options = new ListValueParser(this.getListvalue()).parse();
  }

  public NameValueBean[] getOptions() {
    return options;
  }
}
