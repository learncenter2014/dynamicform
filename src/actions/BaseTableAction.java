/**
 * 
 */
package actions;

import net.sf.json.JSONObject;
import vo.DataQueryVo;
import vo.table.TableActionVo;
import vo.table.TableDataVo;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import bl.common.BaseBusiness;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author gudong
 * @since $Date:2014-02-10$
 */
public class BaseTableAction extends BaseAction implements ModelDriven<DataQueryVo> {

  private static Logger log = LoggerFactory.getLogger(BaseTableAction.class);
  /**
   * 
   */
  private static final long serialVersionUID = -5222876000116738224L;

  private DataQueryVo model;
  private String actionPrex = "";
  private BaseBusiness baseBusiness;
  
  public String getActionPrex() {
    return actionPrex;
  }

  public void setActionPrex(String actionPrex) {
    this.actionPrex = actionPrex;
  }

  public BaseBusiness getBaseBusiness() {
    return baseBusiness;
  }

  public void setBaseBusiness(BaseBusiness baseBusiness) {
    this.baseBusiness = baseBusiness;
  }

  public String index() throws Exception {
    actionPrex = getRequest().getContextPath() + "/datatable";
    return SUCCESS;
  }

  public String initTable() throws Exception {
    TableInitVo init = new TableInitVo();
    boolean headerVisible = false;
    init.getAoColumns().add(new TableHeaderVo("username", "USERNAME"));
    init.getAoColumns().add(new TableHeaderVo("age", "AGE").addSearchOptions(new String[][] { { "1", "0" }, { "Male", "Female" } }));
    init.getAoColumns().add(new TableHeaderVo("address", "ADDRESS", headerVisible));

    if (!headerVisible) {
      init.addAction(new TableActionVo("showDetails", "<img src='" + getRequest().getContextPath()
          + "/jslib/flatlab/assets/advanced-datatable/examples/examples_support/details_open.png" + "'/>"));
    }
    init.addAction(new TableActionVo("edit", "<img src='" + getRequest().getContextPath()
        + "/img/pencil.png" + "'/>").disableAjax());
    init.addAction(new TableActionVo("delete", "<img src='" + getRequest().getContextPath()
        + "/img/edit_remove.png" + "'/>","Are you sure delete?"));
    init.addAction(new TableActionVo("lockUser", "<img src='" + getRequest().getContextPath()
        + "/img/lock.png" + "'/>","Are you sure lock the user?"));

    // json
    JSONObject jsonObject = JSONObject.fromObject(init);
    writeJson(jsonObject);
    return null;
  }

  public String queryTable() throws Exception {
    TableDataVo table = new BaseBusiness().query(getModel());
    table.setiTotalDisplayRecords(100);
    table.setiTotalRecords(100);

    TestGirdData data;
    for (int i = 0; i < getModel().getIDisplayLength(); i++) {
      data = new TestGirdData();
      data.setId(getModel().getIDisplayStart() + (i + 1));
      if (i % 2 == 0)
        data.setAge(1);
      else
        data.setAge(0);
      data.setUsername("test username" + data.getId());
      data.setAddress("test address" + data.getId());
      table.getAaData().add(data);
    }

    // json
    JSONObject jsonObject = JSONObject.fromObject(table);
    writeJson(jsonObject);
    return null;
  }

  @Override
  public DataQueryVo getModel() {
    if (model == null) {
      model = new DataQueryVo();
    }
    return model;
  }
}
