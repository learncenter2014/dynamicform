package core.componentext;

import core.ComponentEnhancer;
import dynamicschema.*;

/**
 * Created by wangronghua on 14-2-12.
 */
public class ComponentBase {
  private int resolution;

  public int getResolution() {
    return resolution;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }


  public ComponentBase enhance(ComponentEnhancer enhancer) {
    if(this instanceof TextEntry) {
      return enhancer.wrap((TextEntry)this);
    } else if(this instanceof CheckboxEntry) {
      return enhancer.wrap((CheckboxEntry)this);
    } else if(this instanceof ImageEntry) {
      return enhancer.wrap((ImageEntry)this);
    } else if(this instanceof DateEntry) {
      return enhancer.wrap((DateEntry)this);
    } else if(this instanceof RadioEntry) {
      return enhancer.wrap((RadioEntry)this);
    } else if(this instanceof SelectEntry) {
      return enhancer.wrap((SelectEntry)this);
    } else if(this instanceof TextAreaEntry) {
      return enhancer.wrap((TextAreaEntry)this);
    } else if(this instanceof Section) {
      return enhancer.wrap((Section)this);
    } else if(this instanceof Form) {
      return enhancer.wrap((Form)this);
    } else if(this instanceof Row) {
      return enhancer.wrap((Row)this);
    } else if(this instanceof Document) {
      return enhancer.wrap((Document)this);
    }
    return enhancer.wrap(this);
  }
}
