/**
 * 
 */
package vo;

import java.io.Serializable;

/**
 * @author gudong
 * 
 */
public class DataQueryVo implements Serializable {
  private int sEcho;
  private int iDisplayStart;
  private int iDisplayLength;

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

}
