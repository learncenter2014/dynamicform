/**
 * 
 */
package vo.table;

import java.util.ArrayList;
import java.util.List;

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

  public boolean isHasDetails() {
    for (TableHeaderVo header : aoColumns) {
      if (!header.isbVisible()) {
        return true;
      }
    }
    return false;
  }
}
