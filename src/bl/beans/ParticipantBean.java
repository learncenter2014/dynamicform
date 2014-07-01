package bl.beans;

import bl.mongobus.ParticipantBusiness;
import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

/**
 * Created by wangronghua on 14-6-29.
 */
@Entity(value = "participant")
public class ParticipantBean extends Bean{

    private String registerNo;      //住院号，门诊号
    private String participantCode; //此对象编码作为随访系统中随访对象的唯一编码，贯穿随访对象的整个生命周期. e.g. 001-002-001-000001
    private Date birthDay;
    private Date dischargeDate;  //出院时间
    private Date startDate;      //随访开始时间，又作基线时间
    private Date latestFollowUpDate; //最近完成随访的时间
    private String studyId;  //随访方案ID
    private String diagnosis;    //诊断

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Date getLatestFollowUpDate() {
        return latestFollowUpDate;
    }

    public void setLatestFollowUpDate(Date latestFollowUpDate) {
        this.latestFollowUpDate = latestFollowUpDate;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getParticipantCode() {
        return participantCode;
    }

    public void setParticipantCode(String participantCode) {
        this.participantCode = participantCode;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }
}
