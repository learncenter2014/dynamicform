package bl.mongobus;

import bl.beans.DiseaseBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by peter on 14-6-21.
 */
public class DiseaseBusiness extends MongoCommonBusiness<BeanContext, DiseaseBean> {
    private static Logger LOG = LoggerFactory.getLogger(DiseaseBusiness.class);

    public DiseaseBusiness() {
        this.dbName = "form";
        this.clazz = DiseaseBean.class;
    }
}
