package core.componentext;

import core.Constants;

/**
 * Created by wangronghua on 14-2-14.
 */
public class ListValueParser {
  private String listString;

  public ListValueParser(String listString) {
    this.listString = listString;
  }

  public NameValueBean[] parse() {
    NameValueBean[] options;
    String listStr = listString;
    if(null != listStr) {
      String[] optionStrs = listStr.split(Constants.PROPERTY_SPLIT);
      options = new NameValueBean[optionStrs.length];
      for(int index = 0; index < optionStrs.length; index ++) {
        String optionStr = optionStrs[index];
        String[] option = optionStr.split(Constants.KEY_VALUE_SPLIT);
        if(option.length > 1) {
          options[index] = new NameValueBean(option[0], option[1]);
        } else if(option.length == 1) {
          options[index] = new NameValueBean(option[0], option[0]);
        } else {
          options[index] = new NameValueBean("NULL", "NULL");
        }
      }
    } else {
      options = new NameValueBean[0];
    }
    return options;
  }
}
