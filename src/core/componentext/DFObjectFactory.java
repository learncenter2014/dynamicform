package core.componentext;

import dynamicschema.ObjectFactory;
import dynamicschema.Text;

/**
 * Created by wangronghua on 14-2-13.
 */
public class DFObjectFactory extends ObjectFactory {

  /**
   * Create an instance of {@link dynamicschema.Text }
   *
   */
  public Text createText() {
    return new TextExtension();
  }
}
