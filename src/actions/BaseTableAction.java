/**
 *
 */
package actions;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.ServletActionContext;
import org.bson.types.ObjectId;
import vo.table.TableDataVo;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;
import bl.common.TableBusinessInterface;

import com.opensymphony.xwork2.ModelDriven;

import javax.servlet.http.HttpServletResponse;

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

    //在ADD DELETE EDIT 增加参数传递
    public String getAddButtonParameter(){
        return "";
    }

  /**
   *
   * @return
   */
  public String getCustomJs() {
    return null;
  };

    public String getCustomJsp() {
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
    //解决对象之间循环关联
    config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
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
    //解决对象之间循环关联
    JsonConfig config = new JsonConfig();
    config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
    JSONObject jsonObject = JSONObject.fromObject(table, config);
    writeJson(jsonObject);
    return null;
  }

    public String exportTable() {
        getModel().setIDisplayStart(0);
        getModel().setIDisplayLength(10000);
        long count = getBusiness().getCount(getModel());
        TableDataVo tableData = getBusiness().query(getModel());

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/msexcel;charset=UTF-8");  //两种方法都可以
        String fileName = this.getTableId()+".xls";
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        //客户端不缓存
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        TableInitVo tiv = getTableInit();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("导出" + sdf.format(new Date()));
            List<TableHeaderVo> tableHeader = tiv.getAoColumns();
            Row row = sheet.createRow(0);
            int columnLength = 0;
            for (int i = 0; i < tableHeader.size(); i++) {
                row.createCell(columnLength).setCellValue(tableHeader.get(i).getsTitle());
                columnLength++;
            }

            int index = 1;
            for (Object vo : tableData.getAaData()) {
                row = sheet.createRow(index);
                int columnLengthData = 0;
                for (int i = 0; i < tableHeader.size(); i++) {
                    TableHeaderVo thv = tableHeader.get(i);
                    //可读性的属性才可以反射
                    if (PropertyUtils.isReadable(vo, thv.getmData()) && PropertyUtils.getProperty(vo, thv.getmData()) != null) {
                        row.createCell(columnLengthData).setCellValue(thv.convertValue(PropertyUtils.getProperty(vo, thv.getmData())));
                    } else {
                        row.createCell(columnLengthData).setCellValue("");
                    }
                    columnLengthData++;
                }
                index++;
            }
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (Exception e) {
            LOG.error("this exception [{}]", e.getMessage());
        }
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
