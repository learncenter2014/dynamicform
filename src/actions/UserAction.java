/**
 * 
 */
package actions;

import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import bl.UserBusiness;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author gudong
 * @since $Date:2014-02-10$
 */
public class UserAction extends BaseTableAction<UserBusiness> {
  private static Logger log = LoggerFactory.getLogger(UserAction.class);
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
  public UserBusiness getBusiness() {
    return UserBusiness.getInstance();
  }

  @Override
  public TableInitVo getTableInit() {
    TableInitVo init = new TableInitVo();
    init.getAoColumns().add(new TableHeaderVo("username", "USERNAME"));
    init.getAoColumns().add(new TableHeaderVo("age", "AGE").addSearchOptions(new String[][] { { "-1", "1", "2" }, { "----", "Male", "Female" } }));
    init.getAoColumns().add(new TableHeaderVo("address", "ADDRESS", false));
    init.setHasDetails(true);

    return init;
  }
}
