package bl.beans;

/**
 * Created by pli on 14-6-28.
 */

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "study_document")
public class StudyDocumentBean extends Bean {
    private String studyId;
    private String documentId;

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
