/**
 * 
 */
package vo.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Table, Map <b>dataTable</b>
 * 
 * @author gudong
 * @since $Date:2014-02-08$
 */
public class TableDataVo implements Serializable {
  private static final long serialVersionUID = -8763407087905944532L;

  private int sEcho;
  private long iTotalRecords;
  private long iTotalDisplayRecords;
  private List aaData = new ArrayList();

  public int getsEcho() {
    return sEcho;
  }

  public void setsEcho(int sEcho) {
    this.sEcho = sEcho;
  }

  public long getiTotalRecords() {
    return iTotalRecords;
  }

  public void setiTotalRecords(long iTotalRecords) {
    this.iTotalRecords = iTotalRecords;
  }

  public long getiTotalDisplayRecords() {
    return iTotalDisplayRecords;
  }

  public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
    this.iTotalDisplayRecords = iTotalDisplayRecords;
  }

  public List getAaData() {
    return aaData;
  }

  public void setAaData(List aaData) {
    this.aaData = aaData;
  }

}
