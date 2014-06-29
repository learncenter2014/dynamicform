package bl.beans;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.List;

/**
 * Created by pli on 14-6-28.
 */
@Entity(value = "study")
public class StudyBean extends Bean {
    private String code;
    private String englishName;

    private String description;
    private short state = 0; //0 草稿 1 发布
    private String diseaseId; //单病种 指向disease主键
    @Transient
    private List<StudyDocumentBean> studyDocumentBeanList;

    public List<StudyDocumentBean> getStudyDocumentBeanList() {
        if(this.studyDocumentBeanList!=null){
            return this.studyDocumentBeanList;
        }
        return super.getSubBeans(StudyDocumentBean.class,"studyId");
    }

    public void setStudyDocumentBeanList(List<StudyDocumentBean> studyDocumentBeanList) {
        this.studyDocumentBeanList = studyDocumentBeanList;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }
}
