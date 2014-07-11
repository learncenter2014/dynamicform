package actions;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import bl.beans.DepartmentBean;
import bl.beans.DocumentBean;
import bl.common.BusinessResult;
import bl.mongobus.DepartmentBusiness;

/**
 * 科室管理
 * 
 * @author zlj Date:2014/07/07
 * @version 1.0
 *
 */
public class DepartmentAction extends BaseTableAction<DepartmentBusiness> {
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DepartmentAction.class);
	private DepartmentBean department;

	@Override
	public String getActionPrex() {
		return getRequest().getContextPath() + "/department";
	}
	
	@Override
	public String getCustomJs() {
		return getRequest().getContextPath() + "/js/department.js";
	};
	
	
	@Override
	public String getCustomJsp() {
		return "/pages/department/deptPost.jsp";
	}
	
	/**
	   * 二级标题
	   */
	  @Override
	    public String getTableTitle() {
	        return "<ul class=\"breadcrumb\"><li>系统管理</li><li class=\"active\"><a href=\"document/index.action\">科室管理</a></li></ul>";
	    }

	/**
	 * 页面查询检索条件
	 * 
	 */
	@Override
	public TableInitVo getTableInit() {
		TableInitVo init = new TableInitVo();
	    init.getAoColumns().add(new TableHeaderVo("uniqueDepartmentId", "科室代码").enableSearch());
	    init.getAoColumns().add(new TableHeaderVo("deptName", "科室名称").enableSearch());
	    // init.getAoColumns().add(new TableHeaderVo("code", "科目编号"));
	    init.getAoColumns().add(new TableHeaderVo("fuRole", "随访角色").addSearchOptions(new String[][] { { "0", "1", "2" }, { "未分配", "管理", "执行" } }).enableSearch());
	    init.getAoColumns().add(new TableHeaderVo("outsideThePrefix", "外线前缀"));
	    init.getAoColumns().add(new TableHeaderVo("longDistanceThePrefix", "拨打长途加拨号码"));
	    init.getAoColumns().add(new TableHeaderVo("hasLongDistance", "是否有长途").addSearchOptions(new String[][] { { "0", "1"}, { "有长途", "无长途" } }).enableSearch());
	    
	    return init;
	}
	
	/**
	 * 保存信息
	 */
	@Override
	  public String save() throws Exception {
		BusinessResult result = null;
		// 添加的场合
	    if (StringUtils.isBlank(department.getId())) {
	    	result = getBusiness().createLeaf(department);
	    } 
	    // 修改的场合
	    else {
            DepartmentBean origBean = (DepartmentBean)getBusiness().getLeaf(department.getId()).getResponseData();
            if (null == origBean) {
              addActionError("未找到对应的科室信息！");
              return INPUT;
            }
            result = getBusiness().updateLeaf(origBean, department);
        }
	    if (result != null && result.getErrors().size() > 0) {
            for (Object error : result.getErrors()) {
                addActionError(error.toString());
            }
            return INPUT;
        }
        if (result != null && result.getMessages().size() > 0) {
            for (Object message : result.getMessages()) {
                addActionMessage(message.toString());
            }
            return SUCCESS;
        }
	    return SUCCESS;
	  }

	/**
	 * 编辑信息
	 */
	  @Override
	  public String edit() throws Exception {
		department = (DepartmentBean) getBusiness().getLeaf(getId()).getResponseData();
	    return SUCCESS;
	  }

	  /**
	   * 删除信息
	   */
	  @Override
	  public String delete() throws Exception {
	    if (getIds() != null) {
	      for (String id : getIds()) {
	        getBusiness().deleteLeaf(id);
	      }
	    }
	    return SUCCESS;
	  }

	public DepartmentBean getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentBean department) {
		this.department = department;
	}
}
