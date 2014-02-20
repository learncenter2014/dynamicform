/**
 * 
 */
package bl.common;

import vo.table.TableQueryVo;
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
    public TableDataVo query(TableQueryVo queryParam) {
        TableDataVo dataTable = new TableDataVo();
        dataTable.setsEcho(queryParam.getSEcho());
        return dataTable;
    }

    public int getCount(TableQueryVo queryParam){
       return 0;
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

    @Override
    public BusinessResult deleteLeaf(String objectId) {
        // TODO Auto-generated method stub
        return null;
    }


}
