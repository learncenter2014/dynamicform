package actions;

import bl.beans.*;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.DocumentBusiness;
import bl.mongobus.StudyBusiness;
import bl.mongobus.StudyPlanBusiness;
import dynamicschema.Document;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by pli on 14-6-19.
 */
public class StudyAction extends BaseTableAction<StudyBusiness> {

    private StudyBean study;
    //当前默认激活的wizard
    private String activeWizard = "wizardStudy";
    private static String[][] wizardAction = new String[][]{{"wizardStudy", "基本信息"}, {"wizardDocument", "模块选择"}, {"wizardView", "页面设计"}, {"wizardPlan", "计划规则"}, {"wizardPreview", "方案预览"}};
    private List<DocumentBean> documentBeanList;
    //这个是来自前台选择的document和对应的Entry
    private List<StudyDocumentBean> savedDocumentBeanList;
    private List<StudyDocumentEntryBean> savedDocumentEntryBeanList;
    private StudyPlanBean subsequentPlan;
    private StudyPlanBean telphonePlan;
    //wizardPlan 复诊随访表
    private List<StudyPlanRuleBean> subsequentPlanRule;
    //wizardPlan 电话随访表
    private List<StudyPlanRuleBean> telphonePlanRule;

    //UI 电话随访表格数据源
    private String subsequentPlanRuleJson = "[]";
    //UI 电话随访表格数据源
    private String telphonePlanRuleJson = "[]";

    public String getSubsequentPlanRuleJson() {
        return subsequentPlanRuleJson;
    }

    public void setSubsequentPlanRuleJson(String subsequentPlanRuleJson) {
        this.subsequentPlanRuleJson = subsequentPlanRuleJson;
    }

    public String getTelphonePlanRuleJson() {
        return telphonePlanRuleJson;
    }

    public void setTelphonePlanRuleJson(String telphonePlanRuleJson) {
        this.telphonePlanRuleJson = telphonePlanRuleJson;
    }

    public List<StudyPlanRuleBean> getSubsequentPlanRule() {
        return subsequentPlanRule;
    }

    public void setSubsequentPlanRule(List<StudyPlanRuleBean> subsequentPlanRule) {
        this.subsequentPlanRule = subsequentPlanRule;
    }

    public List<StudyPlanRuleBean> getTelphonePlanRule() {
        return telphonePlanRule;
    }

    public void setTelphonePlanRule(List<StudyPlanRuleBean> telphonePlanRule) {
        this.telphonePlanRule = telphonePlanRule;
    }

    public StudyPlanBean getSubsequentPlan() {
        return subsequentPlan;
    }

    public void setSubsequentPlan(StudyPlanBean subsequentPlan) {
        this.subsequentPlan = subsequentPlan;
    }

    public StudyPlanBean getTelphonePlan() {
        return telphonePlan;
    }

    public void setTelphonePlan(StudyPlanBean telphonePlan) {
        this.telphonePlan = telphonePlan;
    }

    private static final DocumentBusiness dbs = (DocumentBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DOCUMENTBUSINESS);
    private static final StudyBusiness sbs = (StudyBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_STUDYBUSINESS);
    private static final StudyPlanBusiness sp = (StudyPlanBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_SUTDYPLANBUSINESS);

    public List<StudyDocumentBean> getSavedDocumentBeanList() {
        return savedDocumentBeanList;
    }

    public void setSavedDocumentBeanList(List<StudyDocumentBean> savedDocumentBeanList) {
        this.savedDocumentBeanList = savedDocumentBeanList;
    }

    public List<StudyDocumentEntryBean> getSavedDocumentEntryBeanList() {
        return savedDocumentEntryBeanList;
    }

    public void setSavedDocumentEntryBeanList(List<StudyDocumentEntryBean> savedDocumentEntryBeanList) {
        this.savedDocumentEntryBeanList = savedDocumentEntryBeanList;
    }

    public List<DocumentBean> getDocumentBeanList() {
        return documentBeanList;
    }

    public void setDocumentBeanList(List<DocumentBean> documentBeanList) {
        this.documentBeanList = documentBeanList;
    }

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
        this.edit();
        this.documentBeanList = (List<DocumentBean>) dbs.getAllLeaves().getResponseData();
        return SUCCESS;
    }

    public String savewizardDocument() throws Exception {
        ArrayList<StudyDocumentBean> sdbs = new ArrayList<StudyDocumentBean>();
        if (this.savedDocumentBeanList != null) {
            //constructed all studyDocumentBeans.
            for (int i = 0; i < savedDocumentBeanList.size(); i++) {
                StudyDocumentBean sdb = savedDocumentBeanList.get(i);
                if (sdb != null) {
                    String sdbId = ObjectId.get().toString();
                    sdb.setId(sdbId);
                    sdb.setStudyId(this.study.getId());
                    //追加StudyDocumentEntryBean
                    ArrayList<StudyDocumentEntryBean> sdeb = new ArrayList<StudyDocumentEntryBean>();
                    for (int j = 0; j < this.savedDocumentEntryBeanList.size(); j++) {
                        StudyDocumentEntryBean sdebRef = this.savedDocumentEntryBeanList.get(j);
                        //documentId存在，且要被选中元素也存在
                        if (sdebRef != null && sdebRef.getDocumentId().equals(sdb.getDocumentId()) && sdebRef.getEntryId() != null) {
                            sdebRef.setId(ObjectId.get().toString());
                            sdebRef.setStudyDocumentId(sdbId);
                            sdebRef.setStudyId(this.study.getId());
                            //加入引用
                            sdeb.add(sdebRef);
                        }
                    }
                    sdb.setStudyDocumentEntryBeanList(sdeb);
                    sdbs.add(sdb);
                }
            }
        }
        //持久studyDocument
        sbs.saveStudyDocument(this.study.getId(), sdbs);
        return SUCCESS;
    }

    public String wizardView() throws Exception {
        this.edit();
        return SUCCESS;
    }

    public String savewizardView() throws Exception {
        return SUCCESS;
    }

    public String wizardPlan() throws Exception {
        this.edit();
        List<StudyPlanBean> spbs = this.getStudy().getStudyPlanBeans();
        for (int i = 0; spbs != null && i < spbs.size(); i++) {
            //复诊随访
            if (spbs.get(i).getPlanType() == 0) {
                this.subsequentPlan = spbs.get(i);
                if (this.subsequentPlan != null) {
                    this.subsequentPlanRule = this.subsequentPlan.getStudyPlanRuleBeanList();
                    // json
                    //解决对象之间循环关联
                    JsonConfig config = new JsonConfig();
                    config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                    JSONArray jsonarray = JSONArray.fromObject(this.subsequentPlanRule, config);
                    if (jsonarray.toString().isEmpty()) {
                        this.subsequentPlanRuleJson = "[]";
                    } else {
                        this.subsequentPlanRuleJson = jsonarray.toString();
                    }
                }
            }
            //电话随访
            else if (spbs.get(i).getPlanType() == 1) {
                this.telphonePlan = spbs.get(i);
                if (this.telphonePlan != null) {
                    this.telphonePlanRule = this.telphonePlan.getStudyPlanRuleBeanList();
                    // json
                    //解决对象之间循环关联
                    JsonConfig config = new JsonConfig();
                    config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                    JSONArray jsonarray = JSONArray.fromObject(this.telphonePlanRule, config);
                    if (jsonarray.toString().isEmpty()) {
                        this.telphonePlanRuleJson = "[]";
                    } else {
                        this.telphonePlanRuleJson = jsonarray.toString();
                    }
                }
            }
        }
        return SUCCESS;
    }

    public String savewizardPlan() throws Exception {
        List<StudyPlanBean> spbs = new ArrayList<StudyPlanBean>();
        //更新复诊随访
        if (this.subsequentPlan != null) {
            spbs.add(this.subsequentPlan);
            this.subsequentPlan.set_id(ObjectId.get());
            this.subsequentPlan.setStudyPlanRuleBeanList(this.subsequentPlanRule);
            for (int i = 0; this.subsequentPlanRule != null && i < this.subsequentPlanRule.size(); i++) {
                this.subsequentPlanRule.get(i).setStudyId(this.study.getId());
                this.subsequentPlanRule.get(i).setStudyPlanId(this.subsequentPlan.getId());
            }
        }

        //更新电话随访
        if (this.telphonePlan != null) {
            spbs.add(this.telphonePlan);
            this.telphonePlan.set_id(ObjectId.get());
            this.telphonePlan.setStudyPlanRuleBeanList(this.telphonePlanRule);
            for (int i = 0; this.telphonePlanRule != null && i < this.telphonePlanRule.size(); i++) {
                this.telphonePlanRule.get(i).setStudyId(this.study.getId());
                this.telphonePlanRule.get(i).setStudyPlanId(this.telphonePlan.getId());
            }
        }

        sp.saveStudyPlan(this.study.getId(), spbs);
        return SUCCESS;
    }

    public String wizardPreview() throws Exception {
        this.edit();
        return SUCCESS;
    }

    public String savewizardPreview() throws Exception {
        StudyBean studyFromDb = (StudyBean) getBusiness().getLeaf(this.study.getId()).getResponseData();
        StudyBean orginal = (StudyBean) studyFromDb.clone();
        studyFromDb.setState(this.study.getState());
        getBusiness().updateLeaf(orginal, studyFromDb);
        return SUCCESS;
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>随访设计</li><li class=\"active\"><a href=\"" + getRequest().getContextPath() + "/study/index.action\">方案设计</a></li></ul>";
    }

    public StudyBean getStudy() {
        return study;
    }

    public void setStudy(StudyBean studyBean) {
        this.study = studyBean;
    }

}
