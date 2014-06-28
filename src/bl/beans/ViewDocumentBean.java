package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by pli on 14-6-28.
 */
@Entity(value = "view_document")
public class ViewDocumentBean extends Bean {
    private String viewId;   //指向view表的主键
    private String documentId;//指向document表的主键

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
