package core;

import bl.exceptions.MiServerException;
import dynamicschema.Component;
import dynamicschema.Text;

/**
 * Created by wangronghua on 14-2-11.
 */
public class ComponentEnhancer {

  public static Component enhance(Text text) {

    return text;
  }

  public static Component enhance(Component component) {
    return component;
  }
}
