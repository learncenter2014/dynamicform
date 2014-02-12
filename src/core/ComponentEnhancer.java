package core;

import bl.exceptions.MiServerException;
import core.componentext.ComponentBase;
import dynamicschema.Component;
import dynamicschema.Text;

/**
 * Created by wangronghua on 14-2-11.
 */
public class ComponentEnhancer {

  public static Component wrap(Text text) {

    return text;
  }

  public static ComponentBase wrap(ComponentBase component) {
    return component;
  }
}
