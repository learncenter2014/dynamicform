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
    if(this instanceof Text) {
      return enhancer.wrap((Text)this);
    } else if(this instanceof Checkbox) {
      return enhancer.wrap((Checkbox)this);
    } else if(this instanceof Image) {
      return enhancer.wrap((Image)this);
    } else if(this instanceof Date) {
      return enhancer.wrap((Date)this);
    } else if(this instanceof Radio) {
      return enhancer.wrap((Radio)this);
    } else if(this instanceof Select) {
      return enhancer.wrap((Select)this);
    } else if(this instanceof TextArea) {
      return enhancer.wrap((TextArea)this);
    } else if(this instanceof FieldSet) {
      return enhancer.wrap((FieldSet)this);
    } else if(this instanceof Form) {
      return enhancer.wrap((Form)this);
    } else if(this instanceof Row) {
      return enhancer.wrap((Row)this);
    }
    return enhancer.wrap(this);
  }
}
