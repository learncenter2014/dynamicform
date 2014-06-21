/**
 *
 */
package vo.table;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Table Header, map property <b>Column</b> of <b>dataTable</b>
 *
 * @author gudong
 * @since $Date:2014-02-16$
 */
public class TableHeaderVo {

  private String mData = null; // field Name support object graph, like platform.details.0
  private String sTitle = null; // head title
  private String sClass = ""; // if you want to support mobile, please use 'hidden-phone';
  private String[] asSorting = new String[] { "asc", "desc" }; // [ "desc", "asc","asc", null ]
  private boolean bSortable = false;
  private boolean bSearchable = false;
  private boolean bVisible = true;
  private String suffixed = "";
  //true 仅仅显示在搜索条件里但不出现在grid表头里
  private boolean hiddenColumn = false;

  public boolean isHiddenColumn() {
    return hiddenColumn;
  }

  public TableHeaderVo setHiddenColumn(boolean hiddenColumn) {
    this.hiddenColumn = hiddenColumn;
    return this;
  }

  public String getSuffixed() {
    return suffixed;
  }

  public void setSuffixed(String suffixed) {
    this.suffixed = suffixed;
  }

  public String getsClass() {
    return sClass;
  }

  public void setsClass(String sClass) {
    this.sClass = sClass;
  }

  // // additional properties
  private String[][] searchOptions = null; // map html select element, like [["1","2"]["Male","Female"]]

  public TableHeaderVo(String mData, String sTitle) {
    super();
    this.mData = mData;
    this.sTitle = sTitle;
  }

  public TableHeaderVo(String mData, String sTitle, boolean bVisible) {
    super();
    this.mData = mData;
    this.sTitle = sTitle;
    this.bVisible = bVisible;
    if (bVisible == false) {
      this.bSortable = false;
      this.bSearchable = false;
    }
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

  public TableHeaderVo disableSearch() {
    this.setbSearchable(false);
    return this;
  }

  public TableHeaderVo enableSearch() {
    this.setbSearchable(true);
    return this;
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

  public void setSearchOptions(String[][] searchOptions) {
    this.searchOptions = searchOptions;
  }

  public TableHeaderVo addSearchOptions(String[][] searchOptions) {
    this.searchOptions = searchOptions;
    return this;
  }

  public String[][] getSearchOptions() {
    return searchOptions;
  }

  public String getSClass() {
    return sClass;
  }

  public TableHeaderVo setSClass(String sClass) {
    this.sClass = sClass;
    return this;
  }

  public TableHeaderVo hidePhone(){
    this.sClass = "hidden-phone";
    return this;
  }

  public String convertValue(Object rawValue) {
    /**
     * 由于searchOptions是为了前台下拉框使用的，所以在导出EXCEL数据的时候，可以根据此做值的转化为显示名称，为excel提供友好结果
     * searchOptions数据结构是二位数组，第一纬度：数值 第二纬度：显示名称
     **/
    if (rawValue == null) {
      return "";
    } else if (rawValue instanceof Date) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return sdf.format(rawValue);
    } else if (this.searchOptions != null && rawValue != null) {
      for (int i = 0; i < this.searchOptions[0].length; i++) {
        if (this.searchOptions[0][i].equals(String.valueOf(rawValue))) {
          return this.searchOptions[1][i];
        }
      }

    }
    return rawValue.toString();
  }

    /**
     * cellFormatter["XXXX"] = function ( data, type, full ) {}
     * @return  snippet JS code.
     */
    public String getCellFormatter() {
        if (this.searchOptions != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("cellFormatter['");
            sb.append(this.getmData());
            sb.append("']");
            sb.append(" = function ( data, type, full ) {");
            for (int i = 0; i < this.searchOptions[0].length; i++) {
                sb.append("if(data =='");
                sb.append(this.searchOptions[0][i]);
                sb.append("'){");
                sb.append("return '");
                sb.append(this.searchOptions[1][i]);
                sb.append("';}");
            }
            sb.append("}");
            return sb.toString();
        }
        return null;
    }

    public static void main(String[] args) {
        TableHeaderVo th = new TableHeaderVo("status", "状态").enableSearch().addSearchOptions(
                new String[][] { { "0", "1", "2", "3", "4"}, { "已注册", "通过审核", "通过面试" , "未通过审核", "未通过面试"} });
        System.out.println(th.getCellFormatter());
    }

}
