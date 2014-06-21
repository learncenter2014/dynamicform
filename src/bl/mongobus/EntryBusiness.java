package bl.mongobus;

import bl.beans.EntryBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by peter on 14-6-21.
 */
public class EntryBusiness extends MongoCommonBusiness<BeanContext, EntryBean> {
    private static Logger LOG = LoggerFactory.getLogger(EntryBusiness.class);

    public EntryBusiness() {
        this.dbName = "form";
        this.clazz = EntryBean.class;
    }
}
