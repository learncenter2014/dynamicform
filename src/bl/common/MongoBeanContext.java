package bl.common;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoBeanContext implements BeanContext {

    public MongoBeanContext() {
        this.dbOjbect = new BasicDBObject("_id",new ObjectId());
    }

    private DBObject dbOjbect = null;

    public MongoBeanContext(DBObject dbObject) {
        this.dbOjbect = dbObject;
    }

    public DBObject getDbOjbect() {
        return dbOjbect;
    }

    public void setDbOjbect(DBObject dbOjbect) {
        this.dbOjbect = dbOjbect;
    }

}
