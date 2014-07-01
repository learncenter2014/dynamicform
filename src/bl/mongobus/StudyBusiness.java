package bl.mongobus;

import bl.beans.DocumentBean;
import bl.beans.StudyBean;
import bl.beans.StudyDocumentBean;
import bl.beans.StudyDocumentEntryBean;
import bl.common.BeanContext;
import dao.MongoDBConnectionFactory;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    public List<DocumentBean> getDocumentsByStudyId(String studyId){
        StudyBean sbean = (StudyBean)getLeaf(studyId).getResponseData();
        List<StudyDocumentBean> sdbs = sbean.getStudyDocumentBeanList();
        if(sdbs!=null){
            ArrayList<ObjectId> documents = new ArrayList<ObjectId>();
            for(StudyDocumentBean sd:sdbs){
                documents.add(new ObjectId(sd.getDocumentId()));
            }
            Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
            return dc.createQuery(DocumentBean.class).filter("_id in",documents).asList();
        }else{
            return null;
        }
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

    public void saveStudyDocument(String studyId, List<StudyDocumentBean> listStudyDocument){
        //delete all StudyDocumentBeans
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query queryStudyDocument = dc.createQuery(StudyDocumentBean.class);
        queryStudyDocument.filter("studyId", studyId);
        dc.delete(queryStudyDocument);

        //delete all StudyDocumentEntryBeans
        Query queryStudyDocumentEntry = dc.createQuery(StudyDocumentEntryBean.class);
        queryStudyDocumentEntry.filter("studyId", studyId);
        dc.delete(queryStudyDocumentEntry);

        //persistent all StudyDocumentBeans
        for (int i = 0; i < listStudyDocument.size(); i++) {
            dc.save(listStudyDocument.get(i));
            List<StudyDocumentEntryBean> studyDocumentEntryBeanList = listStudyDocument.get(i).getStudyDocumentEntryBeanList();
            for (int j = 0; j < studyDocumentEntryBeanList.size(); j++) {
                dc.save(studyDocumentEntryBeanList.get(j));
            }
        }
    }
}
