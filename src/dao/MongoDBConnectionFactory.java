package dao;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import util.ServerContext;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import dao.exceptions.FailureDBException;

public class MongoDBConnectionFactory {
    protected static Logger LOG = LoggerFactory.getLogger(MongoDBConnectionFactory.class);
    private static MongoClient mongoClient = null;
    private static Map<String, Datastore> dbRef = new HashMap<String, Datastore>();

    public static void initDb() {
        //注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new SqlDateConverter(null), java.util.Date.class);

        try {
            mongoClient = new MongoClient(ServerContext.getValue("mongodbip"));
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

    public static void destroy() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public static void main(String[] args) {
    }
}
