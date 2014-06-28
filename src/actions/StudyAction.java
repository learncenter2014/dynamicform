package actions;

import bl.beans.StudyBean;
import bl.common.BusinessResult;
import bl.mongobus.StudyBusiness;
import org.apache.commons.lang.StringUtils;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

/**
 * Created by pli on 14-6-19.
 */
public class StudyAction extends BaseTableAction<StudyBusiness> {

    private StudyBean study;
    //当前默认激活的wizard
    private String activeWizard = "wizardStudy";
    private static String[][] wizardAction = new String[][]{{"wizardStudy", "基本信息"}, {"wizardDocument", "模块选择"}, {"wizardView", "页面设计"}, {"wizardPlan", "计划规则"}, {"wizardPreview", "方案预览"}};

    public String getActiveWizard() {
        return activeWizard;
    }

    public void setActiveWizard(String activeWizard) {
        this.activeWizard = activeWizard;
    }

    public static String[][] getWizardAction() {
        return wizardAction;
    }

    public static void setWizardAction(String[][] wizardAction) {
        StudyAction.wizardAction = wizardAction;
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/study";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("code", "方案代码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "方案名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("state", "状态").addSearchOptions(new String[][]{{"0", "1"}, {"草稿", "发布"}}).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("createTime", "创建时间").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("modifyTime", "修改时间").enableSearch());

        return init;
    }

    @Override
    public String save() throws Exception {
        BusinessResult result = null;
        if (StringUtils.isEmpty(study.getId())) {
            result = getBusiness().createLeaf(study);
        } else {
            StudyBean origBean = (StudyBean) getBusiness().getLeaf(study.getId()).getResponseData();
            if (null == origBean) {
                addActionError("未找到对应的模块！");
                return INPUT;
            }
            result = getBusiness().updateLeaf(origBean, study);
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
        study = (StudyBean) getBusiness().getLeaf(getId()).getResponseData();
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
        study = new StudyBean();
        return SUCCESS;
    }

    public String wizardStudy() throws Exception {
        return this.edit();
    }

    public String savewizardStudy() throws Exception {
        return this.save();
    }

    public String wizardDocument() throws Exception {
        return SUCCESS;
    }

    public String savewizardDocument() throws Exception {
        return SUCCESS;
    }

    public String wizardView() throws Exception {
        return SUCCESS;
    }

    public String savewizardView() throws Exception {
        return SUCCESS;
    }

    public String wizardPlan() throws Exception {
        return SUCCESS;
    }

    public String savewizardPlan() throws Exception {
        return SUCCESS;
    }

    public String wizardPreview() throws Exception {
        return SUCCESS;
    }

    public String savewizardPreview() throws Exception {
        return SUCCESS;
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>随访设计</li><li class=\"active\"><a href=\"study/index.action\">方案设计</a></li></ul>";
    }

    public StudyBean getStudy() {
        return study;
    }

    public void setStudy(StudyBean studyBean) {
        this.study = studyBean;
    }

}
