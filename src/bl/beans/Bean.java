package bl.beans;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import bl.common.BeanContext;

public class Bean implements BeanContext, Cloneable, Serializable {
    @Id
    ObjectId _id;
    @Indexed
    String name;
    @Indexed
    Date createTime = null;
    @Indexed
    Date modifyTime = null;

    Boolean isDeleted = false;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
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
     * @param _id
     *            the _id to set
     */
    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    /**
     * @param _id
     *            the _id to set
     */
    public void setId(String _id) {
        if (_id != null && _id.length() > 0) {
            this._id = new ObjectId(_id);
        }
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
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
     * @param modifyTime
     *            the modifytime to set
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
        // It's necessary to provide a default clone() method in this base class
        // in
        // order to allow public access to it, because Object.clone() is
        // protected.
        try {
            return super.clone();
        } catch (CloneNotSupportedException cnse) {
            throw new RuntimeException("Error cloning in Bean", cnse);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Bean that = (Bean) o;

        if (this.getId() != null ? !this.getId().equals(that.getId()) : that
                .getId() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
