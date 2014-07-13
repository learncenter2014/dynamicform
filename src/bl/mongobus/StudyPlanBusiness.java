package bl.mongobus;

import bl.beans.*;
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
 * Created by peter on 14-7-13.
 */
public class StudyPlanBusiness extends MongoCommonBusiness<BeanContext, StudyPlanBean> {
    private static Logger LOG = LoggerFactory.getLogger(StudyPlanBusiness.class);

    public StudyPlanBusiness() {
        this.dbName = "form";
        this.clazz = StudyPlanBean.class;
    }

    public void saveStudyPlan(String studyId, List<StudyPlanBean> listStudyPlan) {
        //delete all StudyPlanBeans
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query queryStudyPlan = dc.createQuery(StudyPlanBean.class);
        queryStudyPlan.filter("studyId", studyId);
        dc.delete(queryStudyPlan);

        //delete all StudyPlanRuleBeans
        Query queryStudyPlanRule = dc.createQuery(StudyPlanRuleBean.class);
        queryStudyPlanRule.filter("studyId", studyId);
        dc.delete(queryStudyPlanRule);

        //persistent all StudyPlanRuleBeans
        for (int i = 0; i < listStudyPlan.size(); i++) {
            dc.save(listStudyPlan.get(i));
            List<StudyPlanRuleBean> studyPlanRuleBeanList = listStudyPlan.get(i).getStudyPlanRuleBeanList();
            for (int j = 0; j < studyPlanRuleBeanList.size(); j++) {
                dc.save(studyPlanRuleBeanList.get(j));
            }
        }
    }
}
