package bl.instancepool;

import java.util.HashMap;
import java.util.Map;

import bl.common.BeanContext;
import bl.common.BusinessInterface;
import bl.exceptions.FailureInitialClassException;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class SingleBusinessPoolManager {
    protected static Logger LOG = LoggerFactory.getLogger(SingleBusinessPoolManager.class);
    /** A cache of business object instances, keyed by their classpaths. */
    private static Map<String, BusinessInterface<BeanContext, BeanContext>> _hBusObjs = new HashMap<String, BusinessInterface<BeanContext, BeanContext>>();
    private static volatile Object syncObj = new Object();

    /**
     * Return a specific type of business object.
     */
    @SuppressWarnings("unchecked")
    public static BusinessInterface<BeanContext, BeanContext> getBusObj(String classpath) {
        LOG.debug("getBusObj {}", classpath);
        BusinessInterface<BeanContext, BeanContext> busObj = _hBusObjs.get(classpath);
        if (busObj == null) {
            // double check this volatile variable for multiple safe.
            synchronized (syncObj) {
                if ((busObj = _hBusObjs.get(classpath)) == null) {
                    try {
                        busObj = (BusinessInterface<BeanContext, BeanContext>) (Class.forName(classpath).newInstance());
                        _hBusObjs.put(classpath, busObj);
                    } catch (Exception ex) {
                        LOG.fatal("Error: couldn't instantiate new business object: {}", ex);
                        throw new FailureInitialClassException(ex);
                    }
                }
            }
        }
        return busObj;
    }
}
