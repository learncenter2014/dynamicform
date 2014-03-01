/**
 * 
 */
package actions;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import bl.UserBusiness;
import bl.beans.UserBean;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author gudong
 * @since $Date:2014-02-10$
 */
public class UserAction extends BaseTableAction<UserBusiness> {
  private static Logger log = LoggerFactory.getLogger(UserAction.class);

  private UserBean user;

  public UserBean getUser() {
    return user;
  }

  public void setUser(UserBean user) {
    this.user = user;
  }

  /**
   * 
   */
  private static final long serialVersionUID = -5222876000116738224L;

  @Override
  public String getActionPrex() {
    return getRequest().getContextPath() + "/user";
  }

  @Override
  public String getCustomJs() {
    return getRequest().getContextPath() + "/js/user.js";
  }

  @Override
  public TableInitVo getTableInit() {
    TableInitVo init = new TableInitVo();
    init.getAoColumns().add(new TableHeaderVo("name", "USERNAME"));
    init.getAoColumns().add(new TableHeaderVo("sex", "Sex").addSearchOptions(new String[][] { { "-1", "1", "2" }, { "----", "Male", "Female" } }));
    init.getAoColumns().add(new TableHeaderVo("lock", "Lock").addSearchOptions(new String[][] { { "-1", "0", "1" }, { "----", "Unlock", "Lock" } }));
    init.getAoColumns().add(new TableHeaderVo("cellPhone", "CELL PHONE", false));
    init.getAoColumns().add(new TableHeaderVo("email", "EMAIL", false));
    return init;
  }

  @Override
  public String save() throws Exception {
    if (StringUtils.isBlank(user.getId())) {
      getBusiness().createLeaf(user);
    } else {
      UserBean origUser = (UserBean) getBusiness().getLeaf(user.getId().toString()).getResponseData();
      BeanUtils.copyProperties(origUser, user);
      getBusiness().updateLeaf(origUser, origUser);
    }
    return SUCCESS;
  }

  @Override
  public String edit() throws Exception {
    user = (UserBean) getBusiness().getLeaf(getId()).getResponseData();
    return SUCCESS;
  }

  @Override
  public String delete() throws Exception {
    if (getIds() != null) {
      for (String id : getIds()) {
        getBusiness().deleteLeaf(id);
      }
    }
    return SUCCESS;
  }

  /**
   * lock or unlock
   * 
   * @return
   * @throws Exception
   */
  public String lock() throws Exception {
    user = (UserBean) getBusiness().getLeaf(getId()).getResponseData();
    if (user != null) {
      if (user.getLock() == UserBean.LOCK) {
        user.setLock(UserBean.UN_LOCK);
      } else {
        user.setLock(UserBean.LOCK);
      }
      getBusiness().updateLeaf(user, user);
    }
    return SUCCESS;
  }
  
}
