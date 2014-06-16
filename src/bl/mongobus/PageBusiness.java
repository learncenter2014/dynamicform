package bl.mongobus;

import bl.beans.PageBean;
import bl.common.BeanContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageBusiness extends MongoCommonBusiness<BeanContext, PageBean> {
    private static Logger LOG = LoggerFactory.getLogger(PageBusiness.class);
    public PageBusiness() {
        this.dbName = "form";
        this.clazz = PageBean.class;
    }
}
