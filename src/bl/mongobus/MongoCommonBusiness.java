package bl.mongobus;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

import bl.common.BeanContext;
import bl.common.BusinessInterface;
import bl.common.BusinessResult;
import bl.exceptions.MiServerException;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import dao.MongoDBConnectionFactory;

public class MongoCommonBusiness<F, L> implements BusinessInterface {
  private static Logger LOG = LoggerFactory.getLogger(MongoCommonBusiness.class);
  // currently, we use a single database for all data.
  protected String dbName = "form";

  protected Class clazz = null;

  @Override
  public BeanContext constructLeafBean() {
    return null;
  }

  @Override
  public BusinessResult createLeaf(BeanContext genLeafBean) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    dc.save(genLeafBean);
    BusinessResult br = new BusinessResult();
    return br;
  }

  @Override
  public BusinessResult getLeaf(String objectId) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    Object object = dc.find(this.clazz, "_id", new ObjectId(objectId)).get();
    BusinessResult br = new BusinessResult();
    br.setResponseData(object);
    return br;
  }

  @Override
  public BusinessResult deleteLeaf(String objectId) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    WriteResult wr = dc.delete(clazz, objectId);
    BusinessResult br = new BusinessResult();
    if (wr.getError() != null) {
      throw new MiServerException.General("error.mongodb.writedata", wr.getError());
    }
    return br;
  }

  @Override
  public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    BusinessResult br = new BusinessResult();
    Key<BeanContext> key = dc.save(newBean);
    br.setResponseData(key.getId());
    return br;
  }

  @Override
  public BusinessResult getAllLeaves() {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    BusinessResult br = new BusinessResult();
    ArrayList<DBObject> dbs = new ArrayList<DBObject>();
    br.setResponseData(dbs);
    return br;
  }


  @Override
  public BusinessResult getLeafByName(String name) {
    // TODO Auto-generated method stub
    return null;
  }

}
