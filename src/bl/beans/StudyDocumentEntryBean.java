package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by pli on 14-6-28.
 */
@Entity(value = "study_document_entry")
public class StudyDocumentEntryBean extends Bean {
    private String studyDocumentId;//指向study_document表的主键
    private String studyId;
    private String documentId;
    private String entryId; //指向entry表的主键

    public String getStudyDocumentId() {
        return studyDocumentId;
    }

    public void setStudyDocumentId(String studyDocumentId) {
        this.studyDocumentId = studyDocumentId;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
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

