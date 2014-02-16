/**
 * 
 */
package actions;

import net.sf.json.JSONObject;
import vo.DataQueryVo;
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

  public String index() throws Exception {
    return SUCCESS;
  }

  public String initTable()throws Exception {
    TableInitVo init = new TableInitVo();
    init.getAoColumns().add(new TableHeaderVo("id", "INDEX"));
    init.getAoColumns().add(new TableHeaderVo("username", "USERNAME"));
    init.getAoColumns().add(new TableHeaderVo("address", "ADDRESS"));
    init.setsAjaxSource(getRequest().getContextPath()+"/datatable/queryTableList.action");
    // json
    JSONObject jsonObject = JSONObject.fromObject(init);
    writeJson(jsonObject);
    return null;
  }
  
  public String queryTableList() throws Exception {
    TableDataVo table = new BaseBusiness().query(getModel());
    table.setiTotalDisplayRecords(100);
    table.setiTotalRecords(100);

    TestGirdData data;
    for (int i = 0; i < getModel().getIDisplayLength(); i++) {
      data = new TestGirdData();
      data.setId(getModel().getIDisplayStart() + (i + 1));
      data.setAge(1);
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
