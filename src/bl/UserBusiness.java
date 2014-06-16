/**
 * 
 */
package bl;

import bl.beans.UserBean;
import bl.common.BeanContext;
import bl.mongobus.MongoCommonBusiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
