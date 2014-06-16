package core.componentext;

import dynamicschema.ObjectFactory;
import dynamicschema.TextEntry;

/**
 * Created by wangronghua on 14-2-13.
 */
public class DFObjectFactory extends ObjectFactory {

  /**
   * Create an instance of {@link dynamicschema.TextEntry }
   *
   */
  public TextEntry createText() {
    return new TextExtension();
  }
}
