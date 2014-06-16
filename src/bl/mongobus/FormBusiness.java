package bl.mongobus;

import bl.beans.TemplateBean;
import bl.common.BeanContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormBusiness extends MongoCommonBusiness<BeanContext, TemplateBean> {
    private static Logger LOG = LoggerFactory.getLogger(FormBusiness.class);
    public FormBusiness() {
        this.dbName = "form";
        this.clazz = TemplateBean.class;
    }
}
