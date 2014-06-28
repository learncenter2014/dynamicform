package bl.mongobus;

import bl.beans.StudyBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by peter on 14-6-21.
 */
public class StudyBusiness extends MongoCommonBusiness<BeanContext, StudyBean> {
    private static Logger LOG = LoggerFactory.getLogger(StudyBusiness.class);

    public StudyBusiness() {
        this.dbName = "form";
        this.clazz = StudyBean.class;
    }
}
