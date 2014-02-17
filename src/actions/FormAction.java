package actions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;

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
  private List<String> filelist = new ArrayList<>();
  private String name = null;
  private String data = null;

  /**
   * @return the file
   */
  public String getFile() {
    return name;
  }

  /**
   * @param file
   *          the file to set
   */
  public void setFile(String file) {
    this.name = file;
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

  public String savexml() {
    if (name != null && data != null) {
      try {
        String requestPath = this.sc.getRealPath(XMLFILEPATH + "/" + name);
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
