package bl.mongobus;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class FormBusiness extends MongoCommonBusiness {
    private static Logger LOG = LoggerFactory.getLogger(FormBusiness.class);

    public FormBusiness() {
        this.dbName = "form";
        this.collectionName = "userdatas";
    }
}
