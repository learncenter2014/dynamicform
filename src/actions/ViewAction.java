package actions;

import bl.beans.StudyBean;
import bl.beans.ViewBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.StudyBusiness;
import bl.mongobus.ViewBusiness;
import org.apache.commons.lang.StringUtils;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

/**
 * Created by peter on 14-6-21.
 */
public class ViewAction extends BaseTableAction<ViewBusiness> {

    private ViewBean view;
    private String studyId;
    private StudyBean studyBean;

    private static final StudyBusiness sbs = (StudyBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_STUDYBUSINESS);

    public StudyBean getStudyBean() {
        return studyBean;
    }

    public void setStudyBean(StudyBean studyBean) {
        this.studyBean = studyBean;
    }

    @Override
    public String getCustomJsp() {
        return "/pages/view/viewDocument.jsp";
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/view";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("sequence", "序号").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "页面名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("type", "页面类型").addSearchOptions(new String[][] { { "0", "1"}, { "随访基线", "随访录入"} }).enableSearch());
        return init;
    }

    @Override
    public String save() throws Exception {
        BusinessResult result = null;
        if (StringUtils.isEmpty(view.getId())) {
            result = getBusiness().createLeaf(view);
        } else {
            result = getBusiness().updateLeaf(view, view);
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

    public String edit() {
        view = (ViewBean) getBusiness().getLeaf(getId()).getResponseData();
        return SUCCESS;
    }

    @Override
    public String delete() throws Exception {
        if (getIds() != null) {
            for (String id : getIds()) {
                getBusiness().deleteLeaf(id);
            }
        }
        return SUCCESS;
    }

    @Override
    public String add() {
        view = new ViewBean();
        view.setStudyId(this.studyId);
        return SUCCESS;
    }

    public String documentList() {
        view = (ViewBean) getBusiness().getLeaf(getId()).getResponseData();
        studyBean = (StudyBean) sbs.getLeaf(this.studyId).getResponseData();
        return SUCCESS;
    }

    @Override
    public String getTableTitle() {
        return "";
    }

    @Override
    public TableQueryVo getModel() {
        TableQueryVo model = super.getModel();
        model.getFilter().put("studyId", this.studyId);
        model.getSort().put("sequence","asc");
        return model;
    }

    @Override
    public String getAddButtonParameter() {
        return "studyId=" + this.studyId;
    }

    public ViewBean getView() {
        return view;
    }

    public void setView(ViewBean view) {
        this.view = view;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }
}
