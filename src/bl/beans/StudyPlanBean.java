package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by pli on 14-6-29.
 */
@Entity(value = "study_plan")
public class StudyPlanBean extends Bean {
    private String studyId;
    private short startPoint = 0;//随访基准时间点  0出院时间 1 门诊时间 2 住院时间
    private short planType; // 0 随访复诊  1 随访电话
    private boolean skipWeek; // 是否顺延周六周日
    private boolean skipVocation; //是否顺延法定节假日
    private boolean matchDeptReservation; //是否顺延科室预约

    public short getPlanType() {
        return planType;
    }

    public void setPlanType(short planType) {
        this.planType = planType;
    }

    public boolean isSkipWeek() {
        return skipWeek;
    }

    public void setSkipWeek(boolean skipWeek) {
        this.skipWeek = skipWeek;
    }

    public boolean isSkipVocation() {
        return skipVocation;
    }

    public void setSkipVocation(boolean skipVocation) {
        this.skipVocation = skipVocation;
    }

    public boolean isMatchDeptReservation() {
        return matchDeptReservation;
    }

    public void setMatchDeptReservation(boolean matchDeptReservation) {
        this.matchDeptReservation = matchDeptReservation;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public short getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(short startPoint) {
        this.startPoint = startPoint;
    }
}
