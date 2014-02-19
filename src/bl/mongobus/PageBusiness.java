package bl.mongobus;

import bl.beans.PageBean;
import bl.common.BeanContext;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class PageBusiness extends MongoCommonBusiness<BeanContext, PageBean> {
    private static Logger LOG = LoggerFactory.getLogger(PageBusiness.class);
    public PageBusiness() {
        this.dbName = "form";
        this.clazz = PageBean.class;
    }
}
