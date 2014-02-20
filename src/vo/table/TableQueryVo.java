/**
 * 
 */
package vo.table;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author gudong
 * 
 */
public class TableQueryVo implements Serializable {
  private int sEcho;
  private int iDisplayStart;
  private int iDisplayLength;

  private Map<String, String> filter = new HashMap<String, String>();
  private LinkedHashMap<String, String> sort = new LinkedHashMap<String, String>();

  public int getSEcho() {
    return sEcho;
  }

  public void setSEcho(int sEcho) {
    this.sEcho = sEcho;
  }

  public int getIDisplayStart() {
    return iDisplayStart;
  }

  public void setIDisplayStart(int iDisplayStart) {
    this.iDisplayStart = iDisplayStart;
  }

  public int getIDisplayLength() {
    return iDisplayLength;
  }

  public void setIDisplayLength(int iDisplayLength) {
    this.iDisplayLength = iDisplayLength;
  }

  public Map<String, String> getFilter() {
    return filter;
  }

  public void setFilter(Map<String, String> filter) {
    this.filter = filter;
  }

  public LinkedHashMap<String, String> getSort() {
    return sort;
  }

  public void setSort(LinkedHashMap<String, String> sort) {
    this.sort = sort;
  }

}
