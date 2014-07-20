package bl.mongobus;

import bl.beans.Bean;
import bl.common.*;
import dao.MongoDBConnectionFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.table.TableDataVo;
import vo.table.TableQueryVo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MongoCommonBusiness<F, L> implements BusinessInterface,
        TableBusinessInterface {
    private static Logger LOG = LoggerFactory
            .getLogger(MongoCommonBusiness.class);
    // currently, we use a single database for all data.
    protected String dbName = "form";

    protected Class<L> clazz = null;

    @Override
    public BeanContext constructLeafBean() {
        return null;
    }

  public String getDBName() {
    String dbFlag = "form";
    return dbFlag;
  }

	@Override
	public BusinessResult createLeaf(BeanContext genLeafBean) {
		Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
		((Bean) genLeafBean)
				.setCreateTime(new Date(System.currentTimeMillis()));
		dc.save(genLeafBean);
		BusinessResult br = new BusinessResult();
		return br;
	}

    @Override
    public BusinessResult getLeaf(String objectId) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Object object = dc.find(this.clazz, "_id", new ObjectId(objectId))
                .get();
        BusinessResult br = new BusinessResult();
        br.setResponseData(object);
        return br;
    }

    @Override
    public BusinessResult deleteLeaf(String objectId) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        BusinessResult br = new BusinessResult();
        Bean obj = (Bean)this.getLeaf(objectId).getResponseData();
        if (obj != null) {
            obj.setIsDeleted(true);
            updateLeaf(obj, obj);
        }

        return br;
    }


    public BusinessResult deleteLeaf(String objectId, boolean realDelete) {
        BusinessResult br = new BusinessResult();
        if(realDelete) {
            Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
            Object object = dc.find(this.clazz, "_id", new ObjectId(objectId))
                    .get();
            dc.delete(object);
        } else {
            return deleteLeaf(objectId);
        }
        return br;
    }

    @Override
    public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        BusinessResult br = new BusinessResult();
        ((Bean) newBean).setCreateTime(((Bean) origBean).getCreateTime());
        ((Bean) newBean).setModifyTime(new Date(System.currentTimeMillis()));
        Key<BeanContext> key = dc.save(newBean);
        br.setResponseData(key.getId());
        return br;
    }

    @Override
    public BusinessResult getAllLeaves() {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        List<L> list = dc.find(this.clazz, "isDeleted", false).asList();
        BusinessResult br = new BusinessResult();
        br.setResponseData(list);
        return br;
    }

    @Override
    public void deleteByCondition(Map filter) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query<L> query = this.constructQuery(filter, null, null);
        dc.delete(query);
    }

    /**
     * 一般的.filter(criteria, value)语法被支持。标准语法是属性名和操作("field >", or
     * "field in")的整合。所有的语法被逻辑"and" 暗暗的联系在一起。
     *
     * <br/>
     * Sort（排序） 你可以通过一个或多个属性名对结果进行升序或降序排序
     *
     * <br/>
     * ... // desc order Query q = <br/>
     * ds.createQuery(MyEntity.class).filter("foo >", 12).order("-dateAdded"); <br/>
     * ... // asc dateAdded, desc foo Query q = <br/>
     * ds.createQuery(MyEntity.class).filter("foo >",
     * 12).order("dateAdded, -foo");
     *
     * <br/>
     * Offset(skip) and limit(size) like as Mysql 你可以通过在查询是指定一个偏移值是服务器跳过一些记录元素。 <br/>
     * 这将比使用几个属性的范围进行查询要低效的多。如下所示：
     *
     * <br/>
     * <br/>
     * Create a filter based on the specified condition and value.
     *
     * <br/>
     * Note: Property is in the form of "name op" ("age >").
     *
     * <br/>
     * Valid operators are ["=", "==","!=", "<>", ">", "<", ">=", "<=", "in",
     * "nin", "all", "size", "exists"] <br/>
     * <br/>
     * Examples: <br/>
     * <br/>
     * filter("yearsOfOperation >", 5) <br/>
     * filter("rooms.maxBeds >=", 2) <br/>
     * filter("rooms.bathrooms exists", 1) <br/>
     * filter("stars in", new Long[]{3,4}) //3 and 4 stars (midrange?) <br/>
     * filter("age >=", age) <br/>
     * filter("age =", age) <br/>
     * filter("age", age) (if no operator, = is assumed) <br/>
     * filter("age !=", age) <br/>
     * filter("age in", ageList) <br/>
     * filter("customers.loyaltyYears in", yearsList)
     *
     * @param filter
     * @param sorted
     * @param spc
     * @return
     */
    @Override
    public List queryDataByCondition(Map filter, Set sorted,
                                     SpecPaginationContext spc) {
        if(null == filter) {
            filter = new HashMap();
        }
        filter.put("isDeleted_!=", true);
        Query query = this.constructQuery(filter, sorted, spc);
        return query.asList();
    }

    @Override
    public List queryDataByCondition(Map filter, Set sorted) {
        return queryDataByCondition(filter, sorted, null);
    }

    /**
     * following with filter, sorted ,spec as query parameters.
     *
     * @param filter
     * @param sorted
     * @param spc
     * @return
     */
    private Query<L> constructQuery(Map filter, Set<String> sorted,
                                    SpecPaginationContext spc) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Query query = dc.createQuery(this.clazz);
        if (filter != null && !filter.isEmpty()) {
            Iterator<String> iterator = filter.keySet().iterator();
            Object obj = null;
            try {
                obj = Class.forName(this.clazz.getName()).newInstance();
            } catch (Exception e) {
                LOG.error("this exception [{}]", e.getMessage());
            }
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = filter.get(key);
                if (value != null && !value.equals("-1") && !value.equals("")) {
                    try {
/**
 * Because in the front page,form name refuses space,<,>
 * as name, so here, we need convert this format
 * "name_gt" "name_lt" name_eq",etc to "name >" "name <"
 * name =",etc.
 */
                        String[] splits = key.split("_");
                        if (splits.length == 2) {
                            String token = splits[1];
                            token = token.replace("lt", "<").replace("gt", ">")
                                    .replace("eq", "=");
                            try {
                                if(PropertyUtils.getPropertyType(obj, splits[0]) == Date.class){
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    //特殊设置，由于前台的传来的数据都是name=[0,1,2..]形式
                                    String[] array = (String[]) value;
                                    BeanUtils.setProperty(obj, splits[0], df.parse(array[0]));
                                }else{
                                    BeanUtils.setProperty(obj, splits[0], value);
                                }
                                query = query.filter(splits[0] + " " + token,
                                        PropertyUtils.getProperty(obj,
                                                splits[0]));
                            } catch (Exception e) {
                                query = query.filter(splits[0] + " " + token,
                                        value);
                            }
                        } else if (splits.length > 2) {
                            String token = splits[splits.length - 1];
                            token = token.replace("lt", "<").replace("gt", ">")
                                    .replace("eq", "=");
                            key = StringUtils.remove(key, "_" + token);
                            query = query.filter(key + " " + token, value);
                        } else {
                            BeanUtils.setProperty(obj, key, value);
                            Object judgeValue = PropertyUtils.getProperty(obj,
                                    key);
                            /**
                             * if this property of bean is string type, here it
                             * is be considered as a wildcard match.
                             */
                            if (judgeValue instanceof String) {
                                if(judgeValue != null && judgeValue.equals("null")){
                                    query = query.filter(key, null);
                                }else if(judgeValue != null && judgeValue.equals("!null")){
                                    query = query.filter(key+" !=", null);
                                    query = query.filter(key+" !=", "");
                                }else{
                                    Pattern pattern = Pattern.compile("^.*"
                                            + judgeValue + ".*$");
                                    query = query.filter(key, pattern);
                                }
                            } else {
                                query = query.filter(key, judgeValue);
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("this exception [{}]", e.getMessage());
                    }
                }
            }
        }

        if (sorted != null && !sorted.isEmpty()) {
            Iterator<String> iterator = sorted.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                query = query.order(key);
            }
        }

        if (spc != null) {
            query = query.offset(spc.getLimitOffset());
            query = query.limit(spc.getLimitSize());
        }
        return query;
    }

    @Override
    public BusinessResult getLeafByName(String name) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        BusinessResult br = new BusinessResult();
        br.setResponseData(dc.find(this.clazz, "name", name).filter("isDeleted", false).get());
        return br;
    }

    @Override
    public TableDataVo query(TableQueryVo queryParam) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
        Set<String> sortedMappingMongo = new HashSet<String>();
        LinkedHashMap<String, String> lhm = queryParam.getSort();
        if (lhm != null) {
            Iterator<String> it = lhm.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = lhm.get(key);
                if (value != null && !value.isEmpty()) {
                    if (value.equals("asc")) {
                        sortedMappingMongo.add(key);
                    } else {
                        sortedMappingMongo.add("-" + key);
                    }
                }
            }
        }
        long count = this.getCount(queryParam);
        int startCounter = queryParam.getIDisplayStart();
        if(queryParam.getIDisplayStart()>count){
            startCounter = 0;
        }
        SpecPaginationContext spc = new SpecPaginationContext();
        spc.setLimitOffset(startCounter);
        spc.setLimitSize(queryParam.getIDisplayLength());
        List<L> dataRecord = queryDataByCondition(queryParam.getFilter(),
                sortedMappingMongo, spc);
        TableDataVo dataTable = new TableDataVo();
        dataTable.setsEcho(queryParam.getSEcho());
        dataTable.setAaData(dataRecord);
        return dataTable;
    }

    @Override
    public long getCount(TableQueryVo queryParam) {
        Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());

        Set<String> sortedMappingMongo = new HashSet<String>();
        LinkedHashMap<String, String> lhm = queryParam.getSort();
        if (lhm != null) {
            Iterator<String> it = lhm.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = lhm.get(key);
                if (value != null && !value.isEmpty()) {
                    if (value.equals("asc")) {
                        sortedMappingMongo.add(key);
                    } else {
                        sortedMappingMongo.add("-" + key);
                    }
                }
            }
        }
        Query<L> query = this.constructQuery(queryParam.getFilter(),
                sortedMappingMongo, null);
        return dc.getCount(query);
	}

  public void updateRecordsByCondition(String targetColumn, Object targetValue, String conditionName, Object conditionValue){
    Datastore dc = MongoDBConnectionFactory.getDatastore(getDBName());
    UpdateOperations<L> ops
        = dc.createUpdateOperations(this.clazz).set(targetColumn, targetValue);
    Query query = dc.createQuery(this.clazz);
    query.filter(conditionName, conditionValue);
    dc.update(query, ops);
    }

    public static void main(String[] args) {
       /* MongoDBConnectionFactory.initDb();
        MongoCommonBusiness<BeanContext, VolunteerTrainCourseBean> mc = new MongoCommonBusiness<BeanContext, VolunteerTrainCourseBean>();
        mc.clazz = VolunteerTrainCourseBean.class;

        Datastore dc = MongoDBConnectionFactory.getDatastore(mc.getDBName());
        Query query = dc.createQuery(mc.clazz);
        query.filter("_id nin", new ObjectId[] { ObjectId.get() });
        System.out.println(dc.getCount(query));*/
    }
}
