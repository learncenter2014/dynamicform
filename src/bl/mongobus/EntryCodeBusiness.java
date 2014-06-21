package bl.mongobus;

import bl.beans.EntryCodeBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangronghua on 14-6-21.
 */
public class EntryCodeBusiness extends MongoCommonBusiness<BeanContext, EntryCodeBean> {
  private static Logger LOG = LoggerFactory.getLogger(EntryCodeBusiness.class);

  public EntryCodeBusiness() {
    this.dbName = "form";
    this.clazz = EntryCodeBean.class;
  }
}
