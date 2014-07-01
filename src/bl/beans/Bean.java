package bl.beans;

import bl.common.BeanContext;
import dao.MongoDBConnectionFactory;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*** 此方法目的：在子表中检索子对象
     *  采用延迟加载的方式来实现，而且在线程上下文只加载一次并且缓存以便提高利用效率
     * @param subClass      字表类型信息
     * @param parentIdName  子表对应外键名字
     * @param <SC>
     * @return  List<SC> 子表类型
     */
    public <SC> List<SC> getSubBeans(Class<SC> subClass, String parentIdName) {
        String key = subClass + parentIdName;
        if(cacheSubBeans.containsKey(key)){
            return cacheSubBeans.get(key);
        }else{
            Datastore dc = MongoDBConnectionFactory.getDatastore("form");
            List<SC> resultList = dc.find(subClass, "isDeleted", false)
                    .filter(parentIdName, this.getId()).asList();
            cacheSubBeans.put(key,resultList);
            return resultList;
        }
    }

    /*** 此方法目的：在父表中检索子对象,根据"_id"字段查询对象
     *  采用延迟加载的方式来实现，而且在线程上下文只加载一次并且缓存以便提高利用效率
     * @param subClass      字表类型信息
     * @param parentIdValue  子表对应外键名字
     * @param <SC>
     * @return  List<SC> 子表类型
     */
    public <SC> SC getParentBean(Class<SC> subClass, String parentIdValue) {
        String key = subClass + parentIdValue;
        if(cacheSubBeans.containsKey(key)){
            return (SC)cacheSubBeans.get(key).get(0);
        }else{
            Datastore dc = MongoDBConnectionFactory.getDatastore("form");
            List<SC> resultList = dc.find(subClass, "isDeleted", false)
                    .filter("_id", new ObjectId(parentIdValue)).asList();
            cacheSubBeans.put(key,resultList);
            return resultList.get(0);
        }
    }

    //缓存池
    private Map<String,List> cacheSubBeans = new HashMap<String,List>();
}
