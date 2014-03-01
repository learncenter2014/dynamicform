/**
 * 
 */
package bl;

import bl.beans.UserBean;
import bl.common.BeanContext;
import bl.mongobus.MongoCommonBusiness;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author gudong
 * @since $Date:2014-02-20$
 */
public class UserBusiness extends MongoCommonBusiness<BeanContext, UserBean> {
  private static Logger log = LoggerFactory.getLogger(UserBusiness.class);

  public UserBusiness() {
    this.dbName = "form";
    this.clazz = UserBean.class;
  }
   
}
