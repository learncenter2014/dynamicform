package bl.mongobus;

import bl.beans.EntryBean;
import bl.beans.EntryCodeBean;
import bl.beans.UnitBean;
import bl.common.BeanContext;
import dao.MongoDBConnectionFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by peter on 14-7-19.
 */
public class UnitBusiness extends MongoCommonBusiness<BeanContext, UnitBean> {
    private static Logger LOG = LoggerFactory.getLogger(UnitBusiness.class);

    public UnitBusiness() {
        this.dbName = "form";
        this.clazz = UnitBean.class;
    }
}
