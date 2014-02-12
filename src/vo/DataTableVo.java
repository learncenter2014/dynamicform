/**
 * 
 */
package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The table
 * 
 * @author gudong
 * 
 */
public class DataTableVo implements Serializable {

  private int sEcho;
  private int iTotalRecords;
  private int iTotalDisplayRecords;
  private List aaData = new ArrayList();

  public int getsEcho() {
    return sEcho;
  }

  public void setsEcho(int sEcho) {
    this.sEcho = sEcho;
  }

  public int getiTotalRecords() {
    return iTotalRecords;
  }

  public void setiTotalRecords(int iTotalRecords) {
    this.iTotalRecords = iTotalRecords;
  }

  public int getiTotalDisplayRecords() {
    return iTotalDisplayRecords;
  }

  public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
    this.iTotalDisplayRecords = iTotalDisplayRecords;
  }

  public List getAaData() {
    return aaData;
  }

  public void setAaData(List aaData) {
    this.aaData = aaData;
  }
}
