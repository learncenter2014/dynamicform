/**
 * 
 */
package bl.common;

import vo.DataQueryVo;
import vo.table.TableDataVo;

/**
 * @author gudong
 * 
 */
public class BaseBusiness<F extends BeanContext, L extends BeanContext> implements BusinessInterface<F, L> {

    /**
     * 
     * @param start
     * @param limt
     * @return
     */
    public TableDataVo query(DataQueryVo query) {
        TableDataVo dataTable = new TableDataVo();
        dataTable.setsEcho(query.getSEcho());
        return dataTable;
    }

    @Override
    public L constructLeafBean() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BusinessResult createLeaf(L genLeafBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BusinessResult getLeaf(String leafUidStr) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BusinessResult deleteLeaf(String leafUidStr) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BusinessResult updateLeaf(L origBean, L newBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BusinessResult getAllLeaves() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BusinessResult getLeafByName(String name) {
      // TODO Auto-generated method stub
      return null;
    }

}
