package actions;

import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import dao.MongoDBConnectionFactory;

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
  private static String DBNAME = "form";
  private static String DBCOLLECTIONNAME = "userdatas";
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
   *          the file to set
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
   *          the data to set
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
      String formatResult = MessageFormat.format(templateString, file, data);
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
      DB db = MongoDBConnectionFactory.getConnection(DBNAME);
      DBCollection dc = db.getCollection(DBCOLLECTIONNAME);
      String requestPath = this.sc.getRealPath(HTMLFILEPATH);
      Path fs = Paths.get(requestPath);
      String[] elements = fs.toFile().list();
      for (String el : elements) {
        if (el.startsWith(PREFORMNAME)) {
          DBCursor dcursor = dc.find(new BasicDBObject("templateFormName", el));
          List<String> list = new ArrayList<String>();
          while (dcursor.hasNext()) {
            list.add(dcursor.next().get(COLLECTIONRECORDID).toString());
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
    DB db = MongoDBConnectionFactory.getConnection(DBNAME);
    DBCollection dc = db.getCollection(DBCOLLECTIONNAME);
    BasicDBObject bdb = new BasicDBObject();
    // edit operation
    if (paraMap.get(COLLECTIONRECORDID) != null) {
      Integer recordId = Integer.valueOf(((String[]) paraMap.get(COLLECTIONRECORDID))[0]);
      paraMap.remove(COLLECTIONRECORDID);
      DBObject dbObje = dc.findOne(new BasicDBObject(COLLECTIONRECORDID, recordId));
      bdb.putAll(dbObje);
    } else {
      // find a max value of recordId in this collection.
      DBCursor dbc = dc.find().sort(new BasicDBObject(COLLECTIONRECORDID, -1)).limit(1);
      int recordId = 1;
      if (dbc.hasNext()) {
        DBObject doj = dbc.next();
        try {
          recordId = Integer.valueOf(String.valueOf(doj.get(COLLECTIONRECORDID))) + 1;
        } catch (NumberFormatException nf) {
          LOG.error("this exception [#0]", nf.getMessage());
        }
      }
      // append recordId for this user data.
      bdb.append(COLLECTIONRECORDID, recordId);
    }
    bdb.putAll(paraMap);
    dc.save(bdb);
    return ActionSupport.SUCCESS;
  }

  public String edituserdata() {
    try {
      Map<String, Object> paraMap = ActionContext.getContext().getParameters();
      DB db = MongoDBConnectionFactory.getConnection(DBNAME);
      DBCollection dc = db.getCollection(DBCOLLECTIONNAME);
      String recordId = ((String[]) paraMap.get(COLLECTIONRECORDID))[0];
      DBCursor dbc = dc.find(new BasicDBObject(COLLECTIONRECORDID, Integer.valueOf(recordId)));
      if (dbc.hasNext()) {
        DBObject dbj = dbc.next();
        String templateFormName = ((List) dbj.get(TEMPLATEFORMNAME)).get(0).toString();
        this.data = templateFormName;
        this.userData = dbj.toMap();
      }
    } catch (Exception e) {
      LOG.error("this exception [#0]", e.getMessage());
    }
    return ActionSupport.SUCCESS;
  }

  public String deleteuserdata() {
    try {
      Map<String, Object> paraMap = ActionContext.getContext().getParameters();
      DB db = MongoDBConnectionFactory.getConnection(DBNAME);
      DBCollection dc = db.getCollection(DBCOLLECTIONNAME);
      String recordId = ((String[]) paraMap.get(COLLECTIONRECORDID))[0];
      DBCursor dbc = dc.find(new BasicDBObject(COLLECTIONRECORDID, Integer.valueOf(recordId)));
      if (dbc.hasNext()) {
        DBObject dbj = dbc.next();
        dc.remove(dbj);
      }
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

  public static void main(String[] args) throws UnknownHostException {
  }

  /**
   * aware injection of struts2
   */
  @Override
  public void setServletContext(ServletContext context) {
    this.sc = context;
  }
}
