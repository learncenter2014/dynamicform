package bl.mongobus;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import bl.common.BeanContext;
import bl.common.BusinessInterface;
import bl.common.BusinessResult;
import bl.common.MongoBeanContext;
import bl.exceptions.MiServerException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import dao.MongoDBConnectionFactory;

public class MongoCommonBusiness implements BusinessInterface<BeanContext, BeanContext> {
    private static Logger LOG = LoggerFactory.getLogger(MongoCommonBusiness.class);
    // currently, we use a single database for all data.
    protected String dbName = "form";
    protected String collectionName = null;

    private DBCollection getConection() {
        DB db = MongoDBConnectionFactory.getConnection(this.dbName);
        DBCollection dc = db.getCollection(this.collectionName);
        return dc;
    }

    @Override
    public BeanContext constructLeafBean() {
        MongoBeanContext mbc = new MongoBeanContext();
        return mbc;
    }

    @Override
    public BusinessResult createLeaf(BeanContext genLeafBean) {
        BusinessResult br = new BusinessResult();
        if (genLeafBean instanceof MongoBeanContext) {
            MongoBeanContext castLeafBean = (MongoBeanContext) genLeafBean;
            WriteResult wr = getConection().save(castLeafBean.getDbOjbect());
            if (wr.getError() != null) {
                throw new MiServerException.General("error.mongodb.writedata", wr.getError());
            }
        }
        return br;
    }

    @Override
    public BusinessResult getLeaf(String objectId) {
        DBCollection dc = getConection();
        DBObject dob = dc.findOne(new BasicDBObject("_id", new ObjectId(objectId)));
        BusinessResult br = new BusinessResult();
        MongoBeanContext createBean = new MongoBeanContext(dob);
        br.setResponseData(createBean);
        return br;
    }

    @Override
    public BusinessResult deleteLeaf(String objectId) {
        DBCollection dc = getConection();
        DBObject dob = dc.findOne(new BasicDBObject("_id", new ObjectId(objectId)));
        WriteResult wr = dc.remove(dob);
        BusinessResult br = new BusinessResult();
        if (wr.getError() != null) {
            throw new MiServerException.General("error.mongodb.writedata", wr.getError());
        }
        return br;
    }

    @Override
    public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
        DBCollection dc = getConection();
        BusinessResult br = new BusinessResult();
        if (newBean instanceof MongoBeanContext) {
            MongoBeanContext castLeafBean = (MongoBeanContext) newBean;
            WriteResult wr = dc.save(castLeafBean.getDbOjbect());
            if (wr.getError() != null) {
                throw new MiServerException.General("error.mongodb.writedata", wr.getError());
            }
        }
        return br;
    }

    @Override
    public BusinessResult getAllLeaves() {
        DBCollection dc = getConection();
        BusinessResult br = new BusinessResult();
        ArrayList<DBObject> dbs = new ArrayList<DBObject>();
        DBCursor dbCorsor = dc.find();
        while (dbCorsor.hasNext()) {
            dbs.add(dbCorsor.next());
        }
        br.setResponseData(dbs);
        return br;
    }

    /**
     * return multiple DBObjects of list by condition parameter.
     */
    @Override
    public BusinessResult findLeaves(BeanContext newBean) {
        DBCollection dc = getConection();
        BusinessResult br = new BusinessResult();
        ArrayList<DBObject> dbs = new ArrayList<DBObject>();
        if (newBean instanceof MongoBeanContext) {
            MongoBeanContext castLeafBean = (MongoBeanContext) newBean;
            DBCursor dbCorsor = dc.find(castLeafBean.getDbOjbect());
            while (dbCorsor.hasNext()) {
                dbs.add(dbCorsor.next());
            }
            br.setResponseData(dbs);
        }
        return br;
    }

}
