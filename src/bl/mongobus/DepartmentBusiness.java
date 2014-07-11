package bl.mongobus;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bl.beans.DepartmentBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import dao.MongoDBConnectionFactory;

/**
 * 科室管理业务处理
 * 
 * @author zlj Date:2014/07/07
 * @version 1.0
 *
 */
public class DepartmentBusiness extends MongoCommonBusiness<BeanContext, DepartmentBean> {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DepartmentBusiness.class);
	
	public DepartmentBusiness() {
	    this.dbName = "form";
	    this.clazz = DepartmentBean.class;
	  }

	/**
	 * 根据code查找Bean
	 * @param code
	 * @return
	 */
	public BusinessResult getLeafByUniqueCode(String code) {
		Datastore dc = MongoDBConnectionFactory.getDatastore(new MongoCommonBusiness<BeanContext, DepartmentBean>().getDBName());
        BusinessResult br = new BusinessResult();
        br.setResponseData(dc.find(DepartmentBean.class, "uniqueDepartmentId", code).filter("isDeleted", false).get());
        return br;
	}
	
	/**
	 * 保存Excel信息
	 */
	public void batchUpdateDeptList(List<DepartmentBean> deptBeanList) {
		Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
		
		for (int i=0; i < deptBeanList.size(); i++) {
			dc.save(deptBeanList.get(i));
		}
	}
	
}
