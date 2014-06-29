package bl.mongobus;

import bl.beans.ParticipantBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangronghua on 14-6-29.
 */
public class ParticipantBusiness extends MongoCommonBusiness<BeanContext, ParticipantBean> {
    private static Logger LOG = LoggerFactory.getLogger(ParticipantBusiness.class);
    public ParticipantBusiness() {
        this.dbName = "form";
        this.clazz = ParticipantBean.class;
    }
}
