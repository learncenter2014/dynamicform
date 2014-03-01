/**
 * @author gudong
 * @since Date: Mar 1, 2014
 */
package bl.common;

import vo.table.TableDataVo;
import vo.table.TableQueryVo;

/**
 * @author gudong
 *
 */
public interface TableBusinessInterface {
  
  /**
   * 
   * @param start
   * @param limt
   * @return
   */
  public TableDataVo query(TableQueryVo queryParam);

  public long getCount(TableQueryVo queryParam);
}
