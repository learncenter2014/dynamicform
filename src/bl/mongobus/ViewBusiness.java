package bl.mongobus;

import bl.beans.ViewBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by peter on 14-6-21.
 */
public class ViewBusiness extends MongoCommonBusiness<BeanContext, ViewBean> {
    private static Logger LOG = LoggerFactory.getLogger(ViewBusiness.class);

    public ViewBusiness() {
        this.dbName = "form";
        this.clazz = ViewBean.class;
    }
}
