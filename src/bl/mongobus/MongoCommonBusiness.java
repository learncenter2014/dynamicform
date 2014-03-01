package bl.mongobus;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

import vo.table.TableDataVo;
import vo.table.TableQueryVo;
import bl.beans.Bean;
import bl.common.BeanContext;
import bl.common.BusinessInterface;
import bl.common.BusinessResult;
import bl.common.TableBusinessInterface;
import bl.exceptions.MiServerException;

import com.mongodb.WriteResult;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import dao.MongoDBConnectionFactory;

public class MongoCommonBusiness<F, L> implements BusinessInterface, TableBusinessInterface {
  private static Logger LOG = LoggerFactory.getLogger(MongoCommonBusiness.class);
  // currently, we use a single database for all data.
  protected String dbName = "form";

  protected Class<L> clazz = null;

  @Override
  public BeanContext constructLeafBean() {
    return null;
  }

  @Override
  public BusinessResult createLeaf(BeanContext genLeafBean) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    ((Bean) genLeafBean).setCreateTime(new Date(System.currentTimeMillis()));
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
    BusinessResult br = new BusinessResult();
    Object obj = this.getLeaf(objectId).getResponseData();
    if (obj != null) {
      WriteResult wr = dc.delete(obj);
      if (wr.getError() != null) {
        throw new MiServerException.General("error.mongodb.writedata", wr.getError());
      }
    }

    return br;
  }

  @Override
  public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    BusinessResult br = new BusinessResult();
    ((Bean) newBean).setModifyTime(new Date(System.currentTimeMillis()));
    Key<BeanContext> key = dc.save(newBean);
    br.setResponseData(key.getId());
    return br;
  }

  @Override
  public BusinessResult getAllLeaves() {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    List<L> list = dc.find(this.clazz).asList();
    BusinessResult br = new BusinessResult();
    br.setResponseData(list);
    return br;
  }

  @Override
  public BusinessResult getLeafByName(String name) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    BusinessResult br = new BusinessResult();
    br.setResponseData(dc.find(this.clazz, "name", name).get());
    return br;
  }

  @Override
  public TableDataVo query(TableQueryVo queryParam) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    TableDataVo dataTable = new TableDataVo();
    dataTable.setsEcho(queryParam.getSEcho());
    dataTable.setAaData(dc.find(this.clazz).asList());
    return dataTable;
  }

  @Override
  public long getCount(TableQueryVo queryParam) {
    Datastore dc = MongoDBConnectionFactory.getDatastore(this.dbName);
    return dc.getCount(this.clazz);
  }

}
