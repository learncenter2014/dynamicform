package bl.mongobus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bl.constants.DBConstants;
import com.mongodb.*;
import org.apache.commons.lang.StringUtils;

import bl.exceptions.MiServerException;

import dao.MongoDBConnectionFactory;
import org.bson.types.ObjectId;

/**
 * Created by wangronghua on 14-2-16.
 */
public class DataBusiness {
    private String dbName = "data";
    private static DataBusiness business = new DataBusiness();
    public static DataBusiness get() {
        return business;
    }

    public Map get(String tableName, String dataId) {
        if(StringUtils.isBlank(tableName) || StringUtils.isBlank(dataId)) {
            return new HashMap();
        }
        DB db = MongoDBConnectionFactory.getConnection(this.dbName);
        DBCollection dc = db.getCollection(tableName);

        BasicDBObject objectQuery = new BasicDBObject("_id", new ObjectId(dataId));

        DBObject dbObject = dc.findOne(objectQuery);
        Map resultMap = null;
        if(dbObject != null) {
            resultMap  = dbObject.toMap();
        }

        return resultMap;
    }

    public List<Map> get(String tableName, String studyId, String participantId) {
        List<Map> resultList = new ArrayList<Map>();
        if(StringUtils.isBlank(tableName) || StringUtils.isBlank(studyId) || StringUtils.isBlank(participantId)) {
            return resultList;
        }
        DB db = MongoDBConnectionFactory.getConnection(this.dbName);
        DBCollection dc = db.getCollection(tableName);

        BasicDBObject objectQuery = new BasicDBObject();
        objectQuery.append(DBConstants.DB_FIELD_STUDYID, studyId).append(DBConstants.DB_FIELD_PARTICIPANTID, participantId);

        DBCursor cursor = dc.find(objectQuery).sort(new BasicDBObject(DBConstants.DB_FIELD_CREATETIME, 1));

        while( cursor.hasNext() ) {
            DBObject object = cursor.next();
            resultList.add(object.toMap());
        }
        return resultList;
    }

    public void insert(String tableName, Map map) {
        if(StringUtils.isBlank(tableName)) {
            return ;
        }
        DB db = MongoDBConnectionFactory.getConnection(this.dbName);
        DBCollection dc = db.getCollection(tableName);
        DBObject dbObject = new BasicDBObject();
        dbObject.putAll(map);
        WriteResult wr = dc.save(dbObject);
        if (wr.getError() != null) {
            throw new MiServerException.General("error.mongodb.writedata",wr.getError());
        }
    }

    public void update(String tableName, String dataId, Map map) {
        if(StringUtils.isBlank(tableName)) {
            return ;
        }
        DB db = MongoDBConnectionFactory.getConnection(this.dbName);
        DBCollection dc = db.getCollection(tableName);
        BasicDBObject objectQuery = new BasicDBObject("_id", new ObjectId(dataId));
        DBObject dbObject = new BasicDBObject();
        dbObject.putAll(map);
        WriteResult wr = dc.update(objectQuery, dbObject);
        if (wr.getError() != null) {
            throw new MiServerException.General("error.mongodb.writedata",wr.getError());
        }
    }

    public void delete() {

    }

}
