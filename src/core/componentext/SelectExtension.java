package core.componentext;

import core.Constants;
import dynamicschema.Select;

/**
 * Created by wangronghua on 14-2-14.
 */
public class SelectExtension extends Select {

  private NameValueBean[] options;

  public void init() {
    options = new ListValueParser(this.getListvalue()).parse();
  }

  public NameValueBean[] getOptions() {
    return options;
  }
}
