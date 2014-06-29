package bl.beans;

/**
 * Created by pli on 14-6-28.
 */

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.List;

@Entity(value = "study_document")
public class StudyDocumentBean extends Bean {
    private String studyId;
    private String documentId;
    @Transient
    private List<StudyDocumentEntryBean> studyDocumentEntryBeanList;

    public List<StudyDocumentEntryBean> getStudyDocumentEntryBeanList() {
        if(this.studyDocumentEntryBeanList!=null){
            return this.studyDocumentEntryBeanList;
        }
        return super.getSubBeans(StudyDocumentEntryBean.class,"studyDocumentId");
    }

    public void setStudyDocumentEntryBeanList(List<StudyDocumentEntryBean> studyDocumentEntryBeanList) {
        this.studyDocumentEntryBeanList = studyDocumentEntryBeanList;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
