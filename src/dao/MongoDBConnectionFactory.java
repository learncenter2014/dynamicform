package dao;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class MongoDBConnectionFactory {
  protected static Logger LOG = LoggerFactory.getLogger(MongoDBConnectionFactory.class);
  private static MongoClient mongoClient = null;

  static {
    try {
      mongoClient = new MongoClient("127.0.0.1");
    } catch (UnknownHostException e) {
      LOG.error("this exception [#0]", e.getMessage());
    }
  }

  public static DB getConnection(String dbname) {
    if (mongoClient != null) {
      DB db = mongoClient.getDB(dbname);
      if (db.getStats().ok()) {
        return db;
      }
    }
    LOG.fatal("this DB status isn't OK.");
    throw new FailureDBException("this DB initialize is failture.");
  }

  public static void main(String[] args) {
    DB db = MongoDBConnectionFactory.getConnection("form");
    DBCollection dc = db.getCollection("userdatas");
    DBCursor dbc = dc.find().sort(new BasicDBObject("recordId",-1)).limit(1);
    DBObject dos = dbc.next();
    System.out.println(dos.get("recordId"));
    // Iterator<DBObject> dbobjects = dbc.iterator();
    // while (dbobjects.hasNext()) {
    // DBObject doj = dbobjects.next();
    // System.out.println("name=" + doj.get("name"));
    // }
    // BasicDBObject bbd = new BasicDBObject();
    // bbd.append("recordId", 3);
    // bbd.append("name", "jackie");
    // bbd.append("age", 14);
    // dc.save(bbd);

  }
}
