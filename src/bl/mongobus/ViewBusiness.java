package bl.mongobus;

import bl.beans.ViewBean;
import bl.beans.ViewDocumentBean;
import bl.common.BeanContext;
import core.TemplateGenerator;
import dao.MongoDBConnectionFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by peter on 14-6-21.
 */
public class ViewBusiness extends MongoCommonBusiness<BeanContext, ViewBean> {
    private static Logger LOG = LoggerFactory.getLogger(ViewBusiness.class);

    public ViewBusiness() {
        this.dbName = "form";
        this.clazz = ViewBean.class;
    }

    public List<ViewDocumentBean> getViewDocumentList(String viewId) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        List<ViewDocumentBean> resultList = dc.find(ViewDocumentBean.class, "isDeleted", false).filter("viewId", viewId).asList();
        return resultList;
    }

    public void saveViewDocumentList(String viewId, List<ViewDocumentBean> viewDocumentBeans) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        //delete all ViewDocumentBeans
        Query viewDocumentQuery = dc.createQuery(ViewDocumentBean.class);
        viewDocumentQuery.filter("viewId", viewId);
        dc.delete(viewDocumentQuery);
        //persistent all ViewDocumentBeans
        for (int i = 0; i < viewDocumentBeans.size(); i++) {
            dc.save(viewDocumentBeans.get(i));
        }

        TemplateGenerator.get().genTemplate(viewId);
    }
}
