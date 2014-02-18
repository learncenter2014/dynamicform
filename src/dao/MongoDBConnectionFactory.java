package dao;

import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import bl.beans.TemplateBean;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import dao.exceptions.FailureDBException;

public class MongoDBConnectionFactory {
  protected static Logger LOG = LoggerFactory.getLogger(MongoDBConnectionFactory.class);
  private static MongoClient mongoClient = null;
  private static Map<String, Datastore> dbRef = new HashMap<String, Datastore>();
  static {
    try {
      mongoClient = new MongoClient("127.0.0.1");
    } catch (UnknownHostException e) {
      LOG.error("this exception [#0]", e.getMessage());
    }
  }

  public static Datastore getDatastore(String dbName) {
    if (dbRef.containsKey(dbName)) {
      return dbRef.get(dbName);
    } else {
      synchronized (MongoDBConnectionFactory.class) {
        if (dbRef.containsKey(dbName)) {
          return dbRef.get(dbName);
        } else {
          Datastore ds = new Morphia().createDatastore(mongoClient, dbName);
          ds.ensureIndexes();
          ds.ensureCaps();
          dbRef.put(dbName, ds);
          return ds;
        }

      }
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
    List<TemplateBean> list = new ArrayList<TemplateBean>();
    {
      TemplateBean tb = new TemplateBean();
      tb.setLabel("测试数据1");
      tb.setName("peter");
      tb.setCreateTime(new Date(System.currentTimeMillis()));
      tb.setModifyTime(new Date(System.currentTimeMillis()));
      list.add(tb);
    }
    {
      TemplateBean tb = new TemplateBean();
      tb.setLabel("测试数据2");
      tb.setName("wit");
      tb.setCreateTime(new Date(System.currentTimeMillis()));
      tb.setModifyTime(new Date(System.currentTimeMillis()));
      list.add(tb);
    }

    {
      TemplateBean tb = new TemplateBean();
      tb.setLabel("测试数据3");
      tb.setName("gudong");
      tb.setCreateTime(new Date(System.currentTimeMillis()));
      tb.setModifyTime(new Date(System.currentTimeMillis()));
      list.add(tb);
    }
    Datastore ds = MongoDBConnectionFactory.getDatastore("form");
    ds.save(list);
  }
}
