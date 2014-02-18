package bl.beans;

import java.sql.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;

import bl.common.BeanContext;

@Index(value = "name")
public class Bean implements BeanContext {
  @Id
  ObjectId _id;

  String name;

  Date createTime;
  Date modifyTime;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the _id
   */
  public ObjectId get_id() {
    return _id;
  }

  /**
   * @param _id
   *          the _id to set
   */
  public void set_id(ObjectId _id) {
    this._id = _id;
  }

  /**
   * @return the createTime
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * @param createTime
   *          the createTime to set
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * @return the modifytime
   */
  public Date getModifyTime() {
    return modifyTime;
  }

  /**
   * @param modifytime
   *          the modifytime to set
   */
  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }
}
