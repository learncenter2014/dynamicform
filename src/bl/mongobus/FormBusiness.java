package bl.mongobus;

import bl.beans.TemplateBean;
import bl.common.BeanContext;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class FormBusiness extends MongoCommonBusiness<BeanContext, TemplateBean> {
    private static Logger LOG = LoggerFactory.getLogger(FormBusiness.class);
    public FormBusiness() {
        this.dbName = "form";
        this.clazz = TemplateBean.class;
    }
}
