package bl.mongobus;

import java.util.regex.Pattern;

import bl.common.BeanContext;
import bl.common.BusinessInterface;
import bl.common.BusinessResult;
import bl.common.MongoBeanContext;
import bl.constants.BusTieConstant;
import bl.exceptions.MiServerException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import dao.MongoDBConnectionFactory;

public class MongoCommonBusiness implements BusinessInterface<BeanContext, BeanContext> {
    private static Logger LOG = LoggerFactory.getLogger(MongoCommonBusiness.class);
    protected String dbName = null;
    protected String collectionName = null;

    private DBCollection getConection() {
        DB db = MongoDBConnectionFactory.getConnection(this.dbName);
        DBCollection dc = db.getCollection(this.collectionName);
        return dc;
    }

    @Override
    public BeanContext constructLeafBean() {
        MongoBeanContext mbc = new MongoBeanContext(new BasicDBObject());
        return mbc;
    }

    @Override
    public BusinessResult createLeaf(BeanContext genLeafBean) {
        BusinessResult br = new BusinessResult();
        if (genLeafBean instanceof MongoBeanContext) {
            MongoBeanContext castLeafBean = (MongoBeanContext) genLeafBean;
            WriteResult wr = getConection().save(castLeafBean.getDbOjbect());
            if (wr.getError() != null) {
                br.setErrors("Cannot stored this data", "");
            }
        }
        return br;
    }

    @Override
    public BusinessResult getLeaf(String leafUidStr) {
        long uid = convertString2Long(leafUidStr);
        return this.getLeaf(uid);

    }

    @Override
    public BusinessResult getLeaf(long leafUidLong) {
        DBObject dob = getConection().findOne(new BasicDBObject(BusTieConstant.COLLECTIONRECORDID, leafUidLong));
        MongoBeanContext genLeafBean = new MongoBeanContext(dob);
        BusinessResult br = new BusinessResult();
        br.setResponseData(genLeafBean);
        return br;
    }

    @Override
    public BusinessResult deleteLeaf(String leafUidStr) {
        long uid = convertString2Long(leafUidStr);
        return this.deleteLeaf(uid);
    }

    @Override
    public BusinessResult deleteLeaf(long leafUidLong) {
        DBCollection dc = getConection();
        DBObject dob = dc.findOne(new BasicDBObject(BusTieConstant.COLLECTIONRECORDID, leafUidLong));
        WriteResult wr = dc.remove(dob);
        BusinessResult br = new BusinessResult();
        if (wr.getError() != null) {
            br.setErrors("Cannot removed this data", "");
        }
        return br;
    }

    @Override
    public BusinessResult updateLeaf(BeanContext origBean, BeanContext newBean) {
        DBCollection dc = getConection();
        BusinessResult br = new BusinessResult();
        if (newBean instanceof MongoBeanContext) {
            MongoBeanContext castLeafBean = (MongoBeanContext) newBean;
            WriteResult wr = dc.save(castLeafBean.getDbOjbect());
            if (wr.getError() != null) {
                br.setErrors("Cannot stored this data", "");
            }
        }
        return br;
    }

    @Override
    public BusinessResult getAllLeaves() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Convert the given string into a long.
     * 
     * @param ctx
     *            The request context.
     * @param theString
     *            The given string value.
     * @return The corresponding long value, or -1 if error.
     */
    public static long convertString2Long(String theString) {
        long retVal = -1;
        if (DIGIT_PATTERN.matcher(theString).matches() == false)
            return retVal; // fix bug#12004
        try {
            retVal = Long.parseLong(theString);
        } catch (NumberFormatException nfe) {
            LOG.warn("Error: couldn't parse the following string into a long: [#0]", nfe);

            // Throw a new exception to describe the problem in the UI.
            throw new MiServerException.General("Error parsing the following string into a long: " + theString);
        }
        return retVal;
    }

    private final static Pattern DIGIT_PATTERN = Pattern.compile("[0-9]+?");
}
