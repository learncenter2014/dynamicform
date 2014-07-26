package bl.mongobus;

import bl.beans.EntryBean;
import bl.beans.EntryCodeBean;
import bl.beans.StudyDocumentBean;
import bl.beans.StudyDocumentEntryBean;
import bl.common.BeanContext;
import dao.MongoDBConnectionFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by peter on 14-6-21.
 */
public class EntryBusiness extends MongoCommonBusiness<BeanContext, EntryBean> {
    private static Logger LOG = LoggerFactory.getLogger(EntryBusiness.class);

    public EntryBusiness() {
        this.dbName = "form";
        this.clazz = EntryBean.class;
    }
    public EntryBean searchEntryByDocumentAndCode(String documentId, String code){
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query queryStudyEntry = dc.createQuery(EntryBean.class);
        EntryBean eb = dc.find(EntryBean.class, "documentId", documentId).filter("code",code).get();
        return eb;
    }

    public void batchUpdateEntryList(String documentId, List<EntryBean> entryBeanList) {
        //delete all entryBean and entryCodeBean
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query queryStudyEntry = dc.createQuery(EntryBean.class);
        List<EntryBean> dbEntryList = dc.find(EntryBean.class, "documentId", documentId).asList();
        for (int i = 0; i < dbEntryList.size(); i++) {
            Query entryCode = dc.createQuery(EntryCodeBean.class).filter("entryId", dbEntryList.get(i).getId());
            dc.delete(entryCode);
        }
        queryStudyEntry.filter("documentId", documentId);
        dc.delete(queryStudyEntry);


        //persistent all entryBean and entryCodeBean
        for (int i = 0; i < entryBeanList.size(); i++) {
            dc.save(entryBeanList.get(i));
            List<EntryCodeBean> entryCodeBeanList = entryBeanList.get(i).getEntryCodeBeanList();
            for (int j = 0; j < entryCodeBeanList.size(); j++) {
                dc.save(entryCodeBeanList.get(j));
            }
        }
    }
}
