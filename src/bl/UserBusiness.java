/**
 * 
 */
package bl;

import vo.table.TableQueryVo;
import vo.table.TableDataVo;
import actions.TestGirdData;
import bl.common.BaseBusiness;

/**
 * @author gudong
 * @since $Date:2014-02-20$
 */
public class UserBusiness extends BaseBusiness {
  private static UserBusiness instance;

  public static UserBusiness getInstance() {
    if (instance == null) {
      instance = new UserBusiness();
    }
    return instance;
  }

  /**
   * 
   * @param start
   * @param limt
   * @return
   */
  public TableDataVo query(TableQueryVo queryParam) {
    TableDataVo dataTable = new TableDataVo();
    dataTable.setsEcho(queryParam.getSEcho());

    TestGirdData data;
    for (int i = 0; i < queryParam.getIDisplayLength(); i++) {
      data = new TestGirdData();
      data.setId(queryParam.getIDisplayStart() + (i + 1));
      if (i % 2 == 0)
        data.setAge(1);
      else
        data.setAge(2);
      data.setUsername("test username" + data.getId());
      data.setAddress("test address" + data.getId());
      dataTable.getAaData().add(data);
    }
    return dataTable;
  }

  public int getCount(TableQueryVo queryParam) {
    return 100;
  }
}
