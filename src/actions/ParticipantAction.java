package actions;

import bl.beans.ParticipantBean;
import bl.beans.PatientBean;
import bl.beans.StudyBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ParticipantBusiness;
import bl.mongobus.PatientBusiness;
import bl.mongobus.StudyBusiness;
import org.apache.commons.beanutils.BeanUtils;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by wangronghua on 14-6-29.
 */
public class ParticipantAction extends BaseTableAction<ParticipantBusiness> {
    private static final StudyBusiness sbBus = (StudyBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_STUDYBUSINESS);
    private static final PatientBusiness patientBus = (PatientBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PATIENTBUSINESS);

    private String patientId;
    private ParticipantBean participant;
    private List<StudyBean> studyList;

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/participant";
    }

    @Override
    public String getCustomJs() {
        return getRequest().getContextPath() + "/js/participant.js";
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>随访执行</li><li class=\"active\"><a href=\"participant/index.action\">随访对象</a></li></ul>";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("registerNo", "住院/门诊号").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "姓名").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("birthDay", "出生年月"));
        init.getAoColumns().add(new TableHeaderVo("dischargeDate", "出院时间"));

        List<StudyBean> studyList = (List<StudyBean>) sbBus.getAllLeaves().getResponseData();
        String[][] studies = new String[2][studyList.size()];
        if (studyList.size() > 0) {
            for (int i = 0; i < studyList.size(); i++) {
                studies[0][i] = String.valueOf(studyList.get(i).getId());
                studies[1][i] = studyList.get(i).getName();
            }
        } else {
            studies = null;
        }
        init.getAoColumns().add(new TableHeaderVo("studyId", "随访方案").addSearchOptions(studies));
        init.getAoColumns().add(new TableHeaderVo("diagnosis", "诊断"));
        init.getAoColumns().add(new TableHeaderVo("participantCode", "对象编码"));
        init.getAoColumns().add(new TableHeaderVo("latestFollowUpDate", "最近一次随访时间"));

        return init;
    }

    public String followUp() {

        return SUCCESS;
    }

    public String register() {
        studyList = (List<StudyBean>) sbBus.getAllLeaves().getResponseData();
        return SUCCESS;
    }

    public String registerSubmit() throws InvocationTargetException, IllegalAccessException {
        if(null != patientId && null != participant) {
            PatientBean patientBean = (PatientBean) patientBus.getLeaf(patientId).getResponseData();
            BeanUtils.copyProperties(participant, patientBean);
            participant.set_id(null);
            this.getBusiness().createLeaf(participant);
        }
        return SUCCESS;
    }

    public List<StudyBean> getStudyList() {
        return studyList;
    }

    public void setStudyList(List<StudyBean> studyList) {
        this.studyList = studyList;
    }

    public ParticipantBean getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantBean participant) {
        this.participant = participant;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

}
