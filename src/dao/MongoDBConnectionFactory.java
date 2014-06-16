package dao;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import util.NullAwareBeanUtilsBean;
import util.ServerContext;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.exceptions.FailureDBException;

public class MongoDBConnectionFactory {
    protected static Logger LOG = LoggerFactory.getLogger(MongoDBConnectionFactory.class);
    private static MongoClient mongoClient = null;
    private static Map<String, Datastore> dbRef = new HashMap<String, Datastore>();

    public static void initDb() {
        //注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new MyDateConvert(), Date.class);
        //non-null will be copied otherwise ignore null value.
        BeanUtilsBean.setInstance(new NullAwareBeanUtilsBean());
        try {
            mongoClient = new MongoClient(ServerContext.getValue("mongodbip"));
        } catch (UnknownHostException e) {
            LOG.error("this exception [{}]", e.getMessage());
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
                    Morphia mophi = new Morphia();
                    Datastore ds = mophi.createDatastore(mongoClient, dbName);
                   // mophi.mapPackage("bl.beans", true);
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
        LOG.error("this DB status isn't OK.");
        throw new FailureDBException("this DB initialize is failture.");
    }

    public static void destroy() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public static void main(String[] args) {
    }

    static class MyDateConvert implements Converter {

        public Date convert(Class arg0, Object arg1) {
            String p = (String)arg1;
            if(p== null || p.trim().length()==0){
                return null;
            }
            try{
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                return df.parse(p.trim());
            }
            catch(Exception e){
                return null;
            }
        }
    }
}
