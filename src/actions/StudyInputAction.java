package actions;

import bl.beans.*;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.DocumentBusiness;
import bl.mongobus.ParticipantBusiness;
import bl.mongobus.StudyBusiness;
import bl.mongobus.ViewBusiness;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangronghua on 14-7-1.
 */
public class StudyInputAction extends BaseAction{

    private List<ViewBean> viewBeanList;
    private String studyId;
    private String participantId;

    public String input() {
        StudyBusiness studyBus =
            (StudyBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_STUDYBUSINESS);
        ViewBusiness viewBus =
            (ViewBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VIEWBUSINESS);
        DocumentBusiness documentBus =
            (DocumentBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DOCUMENTBUSINESS);
        ParticipantBusiness participantBus =
            (ParticipantBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PARTICIPANTBUSINESS);
        if(null != studyId) {
            StudyBean studyBean = (StudyBean) studyBus.getLeaf(studyId).getResponseData();
            viewBeanList = studyBean.getViewBeanList();
        } else if(null != participantId) {
            ParticipantBean participantBean =
                (ParticipantBean) participantBus.getLeaf(participantId).getResponseData();
            if(null != participantBean && null != participantBean.getStudyId()) {
                studyId = participantBean.getStudyId();
                StudyBean studyBean = (StudyBean) studyBus.getLeaf(studyId).getResponseData();
                viewBeanList = studyBean.getViewBeanList();
            }
        }

        if(null != viewBeanList) {
            for(ViewBean view : viewBeanList) {
                List<ViewDocumentBean> viewDocumentBeanList= view.getViewDocumentBeanList();
                List<DocumentBean> documentBeans = new ArrayList<DocumentBean>();
                for(ViewDocumentBean bean : viewDocumentBeanList) {
                    DocumentBean documentBean = (DocumentBean) documentBus.getLeaf(bean.getDocumentId()).getResponseData();
                    if(null != documentBean) {
                        documentBeans.add(documentBean);
                    }
                }
                view.setDocumentBeanList(documentBeans);
            }
        }
        return SUCCESS;
    }


    public List<ViewBean> getViewBeanList() {
        return viewBeanList;
    }

    public void setViewBeanList(List<ViewBean> viewBeanList) {
        this.viewBeanList = viewBeanList;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

}
