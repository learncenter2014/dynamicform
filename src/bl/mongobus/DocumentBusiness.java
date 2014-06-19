package bl.mongobus;

import bl.beans.DocumentBean;
import bl.common.BeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangronghua on 14-6-19.
 */
public class DocumentBusiness extends MongoCommonBusiness<BeanContext, DocumentBean> {
  private static Logger LOG = LoggerFactory.getLogger(DocumentBusiness.class);

  public DocumentBusiness() {
    this.dbName = "form";
    this.clazz = DocumentBean.class;
  }
}
