package actions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.ServletContextAware;

import bl.common.MongoBeanContext;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.FormBusiness;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.opensymphony.xwork2.ActionContext;
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
    private static String HTMLFILEPATH = "html";
    private static String TEMPLATEFILEPATH = "html/template/templatedefinition.html";
    private static String PREFORMNAME = "form_";
    private static String COLLECTIONRECORDID = "recordId";
    private static String TEMPLATEFORMNAME = "templateFormName";
    private List<String> filelist = new ArrayList<>();
    private Map<String, List<String>> filelistrecords = new HashMap<String, List<String>>();
    private String file = null;
    private String data = null;
    private Map userData = null;

    /**
     * @return the userData
     */
    public Map getUserData() {
        return userData;
    }

    public Map<String, List<String>> getFilelistrecords() {
        return filelistrecords;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file
     *            the file to set
     */
    public void setFile(String file) {
        this.file = file;
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

    public String savehtml() {
        try {
            String requestPath = this.sc.getRealPath(HTMLFILEPATH + "/" + PREFORMNAME + file);
            String templatefile = this.sc.getRealPath(TEMPLATEFILEPATH);
            LOG.info("save html content to this [#0] file", templatefile);
            String templateString = new String(Files.readAllBytes(Paths.get(templatefile)), "UTF-8");

            Map<String,String> tokens = new HashMap<String,String>();
            tokens.put("file", file);
            tokens.put("data", data); 
            String patternString = "\\$\\{(" + StringUtils.join(tokens.keySet(), "|") + ")\\}";
            //appendReplacement, appendTail
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(templateString); 
            
            StringBuffer sb = new StringBuffer();
            while(matcher.find()) {
                matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
            }
            matcher.appendTail(sb); 
            
            String formatResult = sb.toString();
            // replace all 'img/ to '../img which due to customized page is stored at html directory.
            formatResult = formatResult.replaceAll("'img/", "'../img/");
            Files.write(Paths.get(requestPath), formatResult.getBytes("UTF-8"));
        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public String savexml() {
        if (file != null && data != null) {
            try {
                String requestPath = this.sc.getRealPath(XMLFILEPATH + "/" + file);
                LOG.info("save xml content to this [#0] file", requestPath);
                Files.write(Paths.get(requestPath), data.getBytes("UTF-8"));
            } catch (Exception e) {
                LOG.error("this exception [#0]", e.getMessage());
            }
        }
        return ActionSupport.SUCCESS;
    }

    public String templateFormList() {

        try {
            String requestPath = this.sc.getRealPath(XMLFILEPATH);
            Path fs = Paths.get(requestPath);
            String[] elements = fs.toFile().list();
            for (String el : elements) {
                filelist.add(el);
            }
            LOG.info("loading all xml #0 files", filelist.toString());
        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public String templateFileList() {
        try {
            String requestPath = this.sc.getRealPath(HTMLFILEPATH);
            Path fs = Paths.get(requestPath);
            String[] elements = fs.toFile().list();
            for (String el : elements) {
                if (el.startsWith(PREFORMNAME)) {
                    FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
                    MongoBeanContext mb = new MongoBeanContext(new BasicDBObject("templateFormName", el));
                    ArrayList<DBObject> dbs = (ArrayList<DBObject>) fb.findLeaves(mb).getResponseData();
                    List<String> list = new ArrayList<String>();
                    for (DBObject db : dbs) {
                        list.add(db.get(COLLECTIONRECORDID).toString());
                    }
                    filelistrecords.put(el, list);
                    filelist.add(el);
                }
            }
            LOG.info("loading all xml [#0] files", filelist.toString());
        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public String storeuserdata() {
        Map<String, Object> paraMap = ActionContext.getContext().getParameters();
        FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
        // edit operation
        if (paraMap.get(COLLECTIONRECORDID) != null) {
            Integer recordId = Integer.valueOf(((String[]) paraMap.get(COLLECTIONRECORDID))[0]);
            paraMap.remove(COLLECTIONRECORDID);
            MongoBeanContext mbc = (MongoBeanContext) fb.getLeaf(recordId).getResponseData();
            DBObject bdb = mbc.getDbOjbect();
            bdb.putAll(paraMap);
            MongoBeanContext orginalMbc = (MongoBeanContext) fb.getLeaf(recordId).getResponseData();
            DBObject orginalDb = orginalMbc.getDbOjbect();
            fb.updateLeaf(new MongoBeanContext(orginalDb), new MongoBeanContext(bdb));
        } else {
            BasicDBObject bdb = (BasicDBObject) ((MongoBeanContext) fb.constructLeafBean()).getDbOjbect();
            bdb.putAll(paraMap);
            fb.updateLeaf(null, new MongoBeanContext(bdb));
        }

        return ActionSupport.SUCCESS;
    }

    public String edituserdata() {
        try {
            Map<String, Object> paraMap = ActionContext.getContext().getParameters();
            FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
            String recordId = ((String[]) paraMap.get(COLLECTIONRECORDID))[0];
            MongoBeanContext mbc = (MongoBeanContext) fb.getLeaf(recordId).getResponseData();
            DBObject db = mbc.getDbOjbect();
            String templateFormName = ((List) db.get(TEMPLATEFORMNAME)).get(0).toString();
            this.data = templateFormName;
            this.userData = db.toMap();
        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public String deleteuserdata() {
        try {
            Map<String, Object> paraMap = ActionContext.getContext().getParameters();
            FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
            String recordId = ((String[]) paraMap.get(COLLECTIONRECORDID))[0];
            fb.deleteLeaf(recordId);
        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    /**
     * @return the filelist
     */
    public List<String> getFileList() {
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
