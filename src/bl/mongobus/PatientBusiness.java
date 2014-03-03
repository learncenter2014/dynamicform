package bl.mongobus;

import bl.beans.PatientBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * Created by wangronghua on 14-3-3.
 */
public class PatientBusiness extends MongoCommonBusiness<BeanContext, PatientBean> {
  private static Logger log = LoggerFactory.getLogger(PatientBusiness.class);

  public PatientBusiness() {
    this.dbName = "form";
    this.clazz = PatientBean.class;
  }

  public BusinessResult getAllLeaves() {
    return super.getAllLeaves();
  }
}
