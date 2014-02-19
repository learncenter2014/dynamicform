/**
 * 
 */
package vo.table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author gudong
 * @since $Date:2014-02-16$
 */
public class TableInitVo {
  private boolean bProcessing = true;
  private boolean bServerSide = true;
  private int iDisplayLength = 10;
  private String[][] aLengthMenu = new String[][] { { "10", "25", "50", "-1" }, { "10", "25", "50", "ALL" } };
  private List<TableHeaderVo> aoColumns = new ArrayList<TableHeaderVo>();

  // //additional properties
  private String idName = "id";
  private String actionHtml = "";
  private Map<String,TableActionVo> actions = new LinkedHashMap<String,TableActionVo>();
  
  public boolean isbProcessing() {
    return bProcessing;
  }

  public void setbProcessing(boolean bProcessing) {
    this.bProcessing = bProcessing;
  }

  public boolean isbServerSide() {
    return bServerSide;
  }

  public void setbServerSide(boolean bServerSide) {
    this.bServerSide = bServerSide;
  }

  public int getiDisplayLength() {
    return iDisplayLength;
  }

  public void setiDisplayLength(int iDisplayLength) {
    this.iDisplayLength = iDisplayLength;
  }

  public String[][] getaLengthMenu() {
    return aLengthMenu;
  }

  public void setaLengthMenu(String[][] aLengthMenu) {
    this.aLengthMenu = aLengthMenu;
  }

  public List<TableHeaderVo> getAoColumns() {
    return aoColumns;
  }

  public void setAoColumns(List<TableHeaderVo> aoColumns) {
    this.aoColumns = aoColumns;
  }

  public String getIdName() {
    return idName;
  }

  public void setIdName(String idName) {
    this.idName = idName;
  }

  public void setActionHtml(String actionHtml) {
    this.actionHtml = actionHtml;
  }

  public String getActionHtml() {
    return actionHtml;
  }

  public void addAction(TableActionVo action) {
    this.actions.put (action.getActionName(),action);
    this.actionHtml += action.toString();
  }

  public Map<String, TableActionVo> getActions() {
    return actions;
  }

  public void setActions(Map<String, TableActionVo> actions) {
    this.actions = actions;
  }

}
