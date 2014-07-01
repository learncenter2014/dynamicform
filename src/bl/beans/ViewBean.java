package bl.beans;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.List;

/**
 * Created by pli on 14-6-28.
 */
@Entity(value = "view")
public class ViewBean extends Bean {
    //研究对象1:n页面条目
    private String studyId;
    private int sequence; //序号
    private String description; //备注或者描述
    private short type; // 0随访基线 1 随访录入

    @Transient
    private String innerHTML;
    @Transient
    private StudyBean study;
    @Transient
    private List<ViewDocumentBean> viewDocumentBeanList;
    @Transient
    private List<DocumentBean> documentBeanList;

    public List<ViewDocumentBean> getViewDocumentBeanList() {
        if(this.viewDocumentBeanList!=null){
            return this.viewDocumentBeanList;
        }
        return super.getSubBeans(ViewDocumentBean.class, "viewId");
    }

    public void setViewDocumentBeanList(List<ViewDocumentBean> viewDocumentBeanList) {
        this.viewDocumentBeanList = viewDocumentBeanList;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getStudyId() {
      return studyId;
    }

    public void setStudyId(String studyId) {
      this.studyId = studyId;
    }

    public String getInnerHTML() {
        return innerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    public StudyBean getStudy() {
        if(this.study!=null){
            return this.study;
        }
        return super.getParentBean(StudyBean.class, this.studyId);
    }

    public void setStudy(StudyBean study) {
        this.study = study;
    }

    public List<DocumentBean> getDocumentBeanList() {
        return documentBeanList;
    }

    public void setDocumentBeanList(List<DocumentBean> documentBeanList) {
        this.documentBeanList = documentBeanList;
    }

}
