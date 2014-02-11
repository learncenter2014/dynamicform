/**
 * 
 */
package actions;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import vo.DataTableVo;

/**
 * @author gudong
 * @since $Date:2014-02-10$
 */
public class DataTableAction extends BaseAction {

  public String queryList() throws Exception {
    JsonConfig aoColumnsConfig = new JsonConfig();
    aoColumnsConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
    aoColumnsConfig.registerJsonValueProcessor(DataTableVo.class, "aoColumns", new JsonValueProcessor() {

      @Override
      public Object processArrayValue(Object arg0, JsonConfig arg1) {
        List<String> colList = (List) arg0;
        JSONArray jsonArray = new JSONArray();
        if (colList != null && colList.size() >0) {
          for(String col : colList){
            jsonArray.add(new JSONObject().element("mData", col));
          }
        }
        return jsonArray;
      }

      @Override
      public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
        return null;
      }

    });
    
    DataTableVo dataTable = new DataTableVo();
    // json
   // JSONArray jsonArray = JSONArray.fromObject(dataTableList, aoColumnsConfig);
    JSONObject jsonObject = JSONObject.fromObject(dataTable);
//    jsonObject.put("data", jsonArray);
//    jsonObject.put("success", true);
    // jsonObject.put("total", total);
    writeJson( jsonObject);
    return SUCCESS;
  }}
