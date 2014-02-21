/**
 * 
 */
package actions;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.sf.json.JSONObject;
import vo.table.TableQueryVo;
import vo.table.TableDataVo;
import vo.table.TableInitVo;
import bl.common.BaseBusiness;

import com.opensymphony.xwork2.ModelDriven;

/**
 * Base Table Action
 * 
 * @author gudong
 * @since $Date:2014-02-20$
 */
public abstract class BaseTableAction<B extends BaseBusiness> extends BaseAction implements ModelDriven<TableQueryVo> {

  private TableQueryVo model;
  private static BaseBusiness business;

  /**
   * The Action Prefix that will be append action. like : getRequest().getContextPath() + "/datatable".
   * 
   * @return
   */
  public abstract String getActionPrex();

  /**
   * 
   * @return
   */
  public String getCustomJs() {
    return null;
  };

  public String getTableTitle() {
    return null;
  }

  public String getTableId() {
    return this.getClass().getSimpleName() + "_table";
  }

  /**
   * 
   * @return
   */
  public abstract TableInitVo getTableInit();

  /**
   * 
   * @return
   */
  public B getBusiness() {
    if (business == null) {
      ParameterizedType t = (ParameterizedType) this.getClass().getGenericSuperclass();
      Type[] ts = t.getActualTypeArguments();
      try {
        business = (B) ((Class<B>) ts[0]).newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
        business = null;
      }
    }
    return (B)business;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String index() throws Exception {
    return "tableIndex";
  }

  /**
   * initTable
   * 
   * @return
   * @throws Exception
   */
  public String initTable() throws Exception {
    // json
    JSONObject jsonObject = JSONObject.fromObject(getTableInit());
    writeJson(jsonObject);
    return null;
  }

  /**
   * queryTable
   * 
   * @return
   * @throws Exception
   */
  public String queryTable() throws Exception {
    int count = getBusiness().getCount(getModel());
    TableDataVo table = getBusiness().query(getModel());
    table.setsEcho(getModel().getSEcho());
    table.setiTotalDisplayRecords(count);
    table.setiTotalRecords(count);

    // json
    JSONObject jsonObject = JSONObject.fromObject(table);
    writeJson(jsonObject);
    return null;
  }

  @Override
  public TableQueryVo getModel() {
    if (model == null) {
      model = new TableQueryVo();
    }
    return model;
  }
}
