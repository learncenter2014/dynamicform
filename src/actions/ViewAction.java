package actions;

import bl.beans.DocumentBean;
import bl.beans.ViewBean;
import bl.beans.ViewDocumentBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.StudyBusiness;
import bl.mongobus.ViewBusiness;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 14-6-21.
 */
public class ViewAction extends BaseTableAction<ViewBusiness> {

    private ViewBean view;
    private String studyId;
    //检索已经被关联的document对象
    private List<DocumentBean> studySelectedDocuments;
    private String[] viewDocumentList;
    private static final StudyBusiness sbs = (StudyBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_STUDYBUSINESS);

    public List<DocumentBean> getStudySelectedDocuments() {
        return studySelectedDocuments;
    }

    public void setStudySelectedDocuments(List<DocumentBean> studySelectedDocuments) {
        this.studySelectedDocuments = studySelectedDocuments;
    }

    public String[] getViewDocumentList() {
        return viewDocumentList;
    }

    public void setViewDocumentList(String[] viewDocumentList) {
        this.viewDocumentList = viewDocumentList;
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
        studySelectedDocuments = sbs.getDocumentsByStudyId(this.studyId);
        return SUCCESS;
    }
    public String saveDocumentList() {
        List<ViewDocumentBean> vdbs = new ArrayList<ViewDocumentBean>();
        if(viewDocumentList!=null){
            for(String ref:viewDocumentList){
                ViewDocumentBean vb = new ViewDocumentBean();
                vb.set_id(new ObjectId());
                vb.setViewId(this.view.getId());
                vb.setDocumentId(ref);
                vdbs.add(vb);
            }
        }
        getBusiness().saveViewDocumentList(this.view.getId(), vdbs);
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
