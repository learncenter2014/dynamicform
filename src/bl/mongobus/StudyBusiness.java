package bl.mongobus;

import bl.beans.StudyBean;
import bl.beans.StudyDocumentBean;
import bl.beans.StudyDocumentEntryBean;
import bl.beans.ViewDocumentBean;
import bl.common.BeanContext;
import dao.MongoDBConnectionFactory;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by peter on 14-6-21.
 */
public class StudyBusiness extends MongoCommonBusiness<BeanContext, StudyBean> {
    private static Logger LOG = LoggerFactory.getLogger(StudyBusiness.class);

    public StudyBusiness() {
        this.dbName = "form";
        this.clazz = StudyBean.class;
    }

    public List<StudyDocumentEntryBean> getStudyDocumentEntryList (String studyId, String documentId) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        List<StudyDocumentEntryBean> resultList = dc.find(StudyDocumentEntryBean.class, "isDeleted", false)
            .filter("studyId", studyId).filter("documentId",documentId).asList();
        return resultList;
    }

    public List<StudyDocumentBean> getStudyDocumentList (String studyId) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        List<StudyDocumentBean> resultList = dc.find(StudyDocumentBean.class, "isDeleted", false)
                .filter("studyId", studyId).asList();
        return resultList;
    }
}
