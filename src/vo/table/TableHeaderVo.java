/**
 * 
 */
package vo.table;

import java.util.Map;

/**
 * Table Header, map property <b>Column</b> of <b>dataTable</b>
 * 
 * @author gudong
 * @since $Date:2014-02-16$
 */
public class TableHeaderVo {

  private String mData = null; // field Name support object graph, like platform.details.0
  private String sTitle = null; // head title
  private String[] asSorting = new String[] { "asc", "desc" }; // [ "desc", "asc","asc", null ]
  private boolean bSortable = true;
  private boolean bSearchable = true;
  private boolean bVisible = true;

  // // additional properties
  private Map<String, String> searchMap = null;

  public TableHeaderVo(String mData, String sTitle) {
    super();
    this.mData = mData;
    this.sTitle = sTitle;
  }

  public String getmData() {
    return mData;
  }

  public void setmData(String mData) {
    this.mData = mData;
  }

  public String getsTitle() {
    return sTitle;
  }

  public void setsTitle(String sTitle) {
    this.sTitle = sTitle;
  }

  public String[] getAsSorting() {
    return asSorting;
  }

  public void setAsSorting(String[] asSorting) {
    this.asSorting = asSorting;
  }

  public boolean isbSortable() {
    return bSortable;
  }

  public void setbSortable(boolean bSortable) {
    this.bSortable = bSortable;
  }

  public boolean isbSearchable() {
    return bSearchable;
  }

  public void setbSearchable(boolean bSearchable) {
    this.bSearchable = bSearchable;
  }

  public boolean isbVisible() {
    return bVisible;
  }

  public void setbVisible(boolean bVisible) {
    this.bVisible = bVisible;
  }

  public Map<String, String> getSearchMap() {
    return searchMap;
  }

  public void setSearchMap(Map<String, String> searchMap) {
    this.searchMap = searchMap;
  }

}
