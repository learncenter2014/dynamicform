/**
 * 
 */
package actions;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.bson.types.ObjectId;
import vo.table.TableDataVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;
import bl.common.TableBusinessInterface;

import com.opensymphony.xwork2.ModelDriven;

/**
 * Base Table Action
 * 
 * @author gudong
 * @since $Date:2014-02-20$
 */
public abstract class BaseTableAction<B extends TableBusinessInterface> extends BaseAction implements ModelDriven<TableQueryVo> {

  private TableQueryVo model;
  private TableBusinessInterface business;
  private String id;
  private String[] ids;
  public static final String INDEX_SUCCESS = "tableIndex";

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String[] getIds() {
    return ids;
  }

  public void setIds(String[] ids) {
    this.ids = ids;
  }

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
    return (B) business;
  }

  @Override
  public TableQueryVo getModel() {
    if (model == null) {
      model = new TableQueryVo();
    }
    return model;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String index() throws Exception {
    return INDEX_SUCCESS;
  }

  /**
   * initTable
   * 
   * @return
   * @throws Exception
   */
  public String initTable() throws Exception {
    // json
    JsonConfig config = new JsonConfig();
    config.setExcludes(new String[] { "searchOptions" });
    JSONObject jsonObject = JSONObject.fromObject(getTableInit(), config);
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
    long count = getBusiness().getCount(getModel());
    TableDataVo table = getBusiness().query(getModel());
    table.setsEcho(getModel().getSEcho());
    table.setiTotalDisplayRecords(count);
    table.setiTotalRecords(count);

    // json
    JSONObject jsonObject = JSONObject.fromObject(table);
    writeJson(jsonObject);
    return null;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String add() throws Exception {
    getSession().setAttribute("dataId", ObjectId.get().toString());
    return SUCCESS;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String edit() throws Exception {
    return SUCCESS;
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String delete() throws Exception {
    return SUCCESS;
  }
  
  /**
   * 
   * @return
   * @throws Exception
   */
  public String save() throws Exception {
    return SUCCESS;
  }
}
