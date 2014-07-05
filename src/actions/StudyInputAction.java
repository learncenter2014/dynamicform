package actions;

import bl.beans.*;
import bl.constants.BusTieConstant;
import bl.constants.DBConstants;
import bl.beans.DynamicDataBean;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.*;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangronghua on 14-7-1.
 */
public class StudyInputAction extends BaseAction{

    private static final StudyBusiness studyBus =
        (StudyBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_STUDYBUSINESS);
    private static final ViewBusiness viewBus =
        (ViewBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VIEWBUSINESS);
    private static final DocumentBusiness documentBus =
        (DocumentBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DOCUMENTBUSINESS);
    private static final ParticipantBusiness participantBus =
        (ParticipantBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PARTICIPANTBUSINESS);

    private List<ViewBean> viewBeanList;
    private List<DynamicDataBean> dynamicDataBeanList;
    private String studyId;
    private String participantId;
    private String viewId;

    public String input() {
        viewBeanList = new ArrayList<ViewBean>();
        dynamicDataBeanList = new ArrayList<DynamicDataBean>();
        if(StringUtils.isEmpty(participantId)) {
            return FAILURE;
        }
        if(null == studyId) {
            ParticipantBean participantBean =
                (ParticipantBean) participantBus.getLeaf(participantId).getResponseData();
            if(null != participantBean && null != participantBean.getStudyId()) {
                studyId = participantBean.getStudyId();
            }
        }

        if(null != studyId) {
            StudyBean studyBean = (StudyBean) studyBus.getLeaf(studyId).getResponseData();
            if(null != studyBean) {
                List<Map> dataList = DataBusiness.get().get(studyBean.getCode(), studyId, participantId);
                for(Map data : dataList) {
                    String id = ((ObjectId) data.get(DBConstants.DB_FIELD_ID)).toString();
                    String viewId = (String) data.get(DBConstants.DB_FIELD_VIEWID);
                    Date createTime = (Date) data.get(DBConstants.DB_FIELD_CREATETIME);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    ViewBean view = (ViewBean) viewBus.getLeaf(viewId).getResponseData();
                    view.setName(view.getName() + "(" + sdf.format(createTime) + ")");

                    DynamicDataBean dataBean = new DynamicDataBean();
                    dataBean.setDataRecordId(id);
                    dataBean.setView(view);
                    dataBean.setTableName(studyBean.getCode());
                    viewBeanList.add(view);
                    dynamicDataBeanList.add(dataBean);
                }
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

    public String selectView() {
        if(null != studyId) {
            StudyBean studyBean = (StudyBean) studyBus.getLeaf(studyId).getResponseData();
            viewBeanList = studyBean.getViewBeanList();
        }
        return SUCCESS;
    }

    public String saveView() {
        Map param = new HashMap();
        if(StringUtils.isEmpty(studyId) || StringUtils.isEmpty(participantId) || StringUtils.isEmpty(viewId)) {
            return FAILURE;
        }
        StudyBean studyBean = (StudyBean) studyBus.getLeaf(studyId).getResponseData();

        if(null != studyBean && StringUtils.isNotEmpty(studyBean.getCode())) {
            param.put(DBConstants.DB_FIELD_STUDYID, studyId);
            param.put(DBConstants.DB_FIELD_PARTICIPANTID, participantId);
            param.put(DBConstants.DB_FIELD_VIEWID, viewId);
            param.put(DBConstants.DB_FIELD_CREATETIME, new Date());
            DataBusiness.get().insert(studyBean.getCode(), param);
            return SUCCESS;
        }
        return FAILURE;
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

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public List<DynamicDataBean> getDynamicDataBeanList() {
        return dynamicDataBeanList;
    }

    public void setDynamicDataBeanList(List<DynamicDataBean> dynamicDataBeanList) {
        this.dynamicDataBeanList = dynamicDataBeanList;
    }


}
