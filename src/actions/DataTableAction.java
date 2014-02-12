/**
 * 
 */
package actions;

import net.sf.json.JSONObject;
import vo.DataQueryVo;
import vo.DataTableVo;
import bl.common.BaseBusiness;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author gudong
 * @since $Date:2014-02-10$
 */
public class DataTableAction extends BaseAction implements ModelDriven<DataQueryVo> {

  private static Logger log = LoggerFactory.getLogger(DataTableAction.class);
  /**
   * 
   */
  private static final long serialVersionUID = -5222876000116738224L;

  private DataQueryVo model;

  public String index() throws Exception {
    return SUCCESS;
  }

  public String queryList() throws Exception {
    DataTableVo dataTable = new BaseBusiness().query(getModel());
    dataTable.setiTotalDisplayRecords(100);
    dataTable.setiTotalRecords(100);

    // if (getModel().getiDisplayLength() == 0) {
    // getModel().setiDisplayLength(10);
    // }

    TestGirdData data;
    for (int i = 0; i < getModel().getIDisplayLength(); i++) {
      data = new TestGirdData();
      data.setId(getModel().getIDisplayStart() + (i+1));
      data.setAge(1);
      data.setUsername("test username" + data.getId());
      data.setAddress("test address" + data.getId());
      dataTable.getAaData().add(data);
    }

    // json
    JSONObject jsonObject =  JSONObject.fromObject(dataTable);
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
