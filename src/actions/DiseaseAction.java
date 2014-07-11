package actions;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import bl.beans.DiseaseBean;
import bl.common.BusinessResult;
import bl.mongobus.DiseaseBusiness;

/**
 * 单病种管理
 * 
 * @author zlj Date: 2014/07/09
 * @version 1.0
 *
 */
public class DiseaseAction extends BaseTableAction<DiseaseBusiness>{
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DiseaseAction.class);
	private DiseaseBean disease;

	@Override
	public String getActionPrex() {
		return getRequest().getContextPath() + "/disease";
	}

	@Override
	public String getCustomJsp() {
		return "/pages/disease/diseasePost.jsp";
	}
	
	@Override
	public String getTableTitle() {
		return "<ul class=\"breadcrumb\"><li>系统管理</li><li class=\"active\"><a href=\"document/index.action\">单病种管理</a></li></ul>";
	}
	
	@Override
	public TableInitVo getTableInit() {
		TableInitVo init = new TableInitVo();
		init.getAoColumns().add(new TableHeaderVo("system", "病种所属系统").enableSearch());
		init.getAoColumns().add(new TableHeaderVo("name", "疾病名称").enableSearch());
		init.getAoColumns().add(new TableHeaderVo("ICD10", "ICD-10").enableSearch());
		init.getAoColumns().add(new TableHeaderVo("operationName", "手术名称"));
		init.getAoColumns().add(new TableHeaderVo("ICD9CM3", "ICD-9-CM-3").enableSearch());
		init.getAoColumns().add(new TableHeaderVo("content", "备注(病例数）"));
		
		return init;
	}
	
	/**
	 * 保存信息
	 */
	@Override
	public String save() throws Exception {
		BusinessResult result = null;
		// 添加单病种的场合
		if (StringUtils.isBlank(disease.getId())) {
			result = getBusiness().createLeaf(disease);
		}
		// 修改单病种信息的场合
		else {
			DiseaseBean orignBean = (DiseaseBean)getBusiness().getLeaf(disease.getId()).getResponseData();
			if (null == orignBean) {
				addActionError("未找到对应的单病种信息！");
				return INPUT;
			}
			result = getBusiness().updateLeaf(orignBean, disease);
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
	public String edit() throws Exception {
		disease = (DiseaseBean) getBusiness().getLeaf(getId()).getResponseData();
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

	public DiseaseBean getDisease() {
		return disease;
	}

	public void setDisease(DiseaseBean disease) {
		this.disease = disease;
	}
}
