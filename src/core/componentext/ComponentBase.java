package core.componentext;

import core.ComponentEnhancer;

/**
 * Created by wangronghua on 14-2-12.
 */
public class ComponentBase {
  public ComponentBase enhance(ComponentEnhancer enhancer) {
    return enhancer.wrap(this);
  }
}
