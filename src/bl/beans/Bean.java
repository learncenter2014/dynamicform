package bl.beans;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;

import bl.common.BeanContext;

@Index(value = "name")
public class Bean implements BeanContext, Cloneable {
    @Id
    ObjectId _id;

    String name;

    Date createTime = new Date(System.currentTimeMillis());
    Date modifyTime = new Date(System.currentTimeMillis());

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
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
     * @return the _id
     */
    public String getId() {
        return _id != null ? _id.toString() : "";
    }

    /**
     * @param _id the _id to set
     */
    public void set_id(ObjectId _id) {
        this._id = _id;
    }
    /**
     * @param _id the _id to set
     */
    public void setId(String _id) {
        this._id = new ObjectId(_id);
    }
    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
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
     * @param modifyime the modifytime to set
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * Clone (create a deep copy of) this instance.
     *
     * @return A clone of this instance.
     */
    public Object clone() {
        // It's necessary to provide a default clone() method in this base class in
        // order to allow public access to it, because Object.clone() is protected.
        try {
            return super.clone();
        } catch (CloneNotSupportedException cnse) {
            throw new RuntimeException("Error cloning in Bean", cnse);
        }
    }
}
