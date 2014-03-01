package bl.mongobus;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import bl.exceptions.MiServerException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import dao.MongoDBConnectionFactory;

/**
 * Created by wangronghua on 14-2-16.
 */
public class DataBusiness {
  private String dbName = "data";
  private static DataBusiness business = new DataBusiness();
  public static DataBusiness get() {
    return business;
  }

  public Map get(String templateId, String dataId, String pageName, String userId) {
    if(StringUtils.isBlank(templateId)) {
      return new HashMap();
    }
    DB db = MongoDBConnectionFactory.getConnection(this.dbName);
    DBCollection dc = db.getCollection(templateId);

    BasicDBObject objectQuery = new BasicDBObject("dataId", dataId);
    if (pageName != null) {
      objectQuery.append("pageName", pageName);
    }
    if (userId != null) {
      objectQuery.append("userId", userId);
    }
    
    DBObject dbObject = dc.findOne(objectQuery);
    Map resultMap = null;
    if(dbObject != null) {
      resultMap  = dbObject.toMap();
    }

    return resultMap;
  }

  public void insert(String templateId, Map map) {
    if(StringUtils.isBlank(templateId)) {
      return ;
    }
    DB db = MongoDBConnectionFactory.getConnection(this.dbName);
    DBCollection dc = db.getCollection(templateId);
    DBObject dbObject = new BasicDBObject();
    dbObject.putAll(map);
    WriteResult wr = dc.save(dbObject);
    if (wr.getError() != null) {
      throw new MiServerException.General("error.mongodb.writedata",wr.getError());
    }
  }

  public void update(String templateId, String dataId, Map map) {
    if(StringUtils.isBlank(templateId)) {
      return ;
    }
    DB db = MongoDBConnectionFactory.getConnection(this.dbName);
    DBCollection dc = db.getCollection(templateId);
    BasicDBObject objectQuery = new BasicDBObject("dataId", dataId);
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
