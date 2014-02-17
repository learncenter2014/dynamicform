package actions;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;

import bl.common.BusinessResult;
import bl.common.MongoBeanContext;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.FormBusiness;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * This is a dynamic handler action
 * 
 * @author peter
 * 
 */
public class FormAction extends ActionSupport implements ServletContextAware {
    protected static Logger LOG = LoggerFactory.getLogger(FormAction.class);
    private ServletContext sc = null;
    private static String XMLFILEPATH = "xml";
    private List<String[]> filelist = new ArrayList<String[]>();
    private String name = null;
    private String data = null;
    private String label = null;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the file
     */
    public String getName() {
        return name;
    }

    /**
     * @param file
     *            the file to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String execute() throws Exception {
        return super.execute();
    }

    public String savexml() {
        if (this.name != null && this.data != null && this.label != null) {
            try {
                String requestPath = this.sc.getRealPath(XMLFILEPATH + "/" + name + ".xml");
                LOG.info("save xml content to this [#0] file", requestPath);
                Files.write(Paths.get(requestPath), this.data.getBytes("UTF-8"));

                // stored in template table, refer to dynamictable.js
                FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
                MongoBeanContext mb = new MongoBeanContext(new BasicDBObject("name", this.name));
                BusinessResult br = fb.findLeaves(mb);
                ArrayList<DBObject> al = (ArrayList<DBObject>) br.getResponseData();
                if (al.size() > 0) {
                    BasicDBObject orignalDb = (BasicDBObject) al.get(0);
                    BasicDBObject newDb = (BasicDBObject) orignalDb.clone();
                    newDb.put("label", this.label);
                    newDb.put("modifytime", new Date(System.currentTimeMillis()));
                    fb.updateLeaf(new MongoBeanContext(orignalDb), new MongoBeanContext(newDb));
                } else {
                    MongoBeanContext createM = (MongoBeanContext) fb.constructLeafBean();
                    DBObject newdb = createM.getDbOjbect();
                    newdb.put("name", this.name);
                    newdb.put("label", this.label);
                    newdb.put("path", "xml/" + this.name + ".xml");
                    newdb.put("createtime", new Date(System.currentTimeMillis()));
                    fb.createLeaf(createM);
                }
            } catch (Exception e) {
                LOG.error("this exception [#0]", e.getMessage());
            }
        }
        return ActionSupport.SUCCESS;
    }

    public String templateFormList() {

        try {
            FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
            //find all records in the template;
            BusinessResult br = fb.getAllLeaves();
            ArrayList<DBObject> elements = (ArrayList<DBObject>) br.getResponseData();
            for (DBObject el : elements) {
                String[] row = new String[] { el.get("label").toString(), el.get("path").toString() };
                this.filelist.add(row);
            }
            LOG.info("loading all xml #0 files", String.valueOf(filelist.size()));
        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    /**
     * @return the filelist
     */
    public List<String[]> getFileList() {
        return filelist;
    }

    public static void main(String[] args) {
    }

    /**
     * aware injection of struts2
     */
    @Override
    public void setServletContext(ServletContext context) {
        this.sc = context;
    }
}
