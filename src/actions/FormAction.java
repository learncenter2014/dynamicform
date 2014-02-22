package actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;

import util.ServerContext;

import bl.beans.TemplateBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.FormBusiness;

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
    protected final static Logger LOG = LoggerFactory.getLogger(FormAction.class);
    private ServletContext sc = null;
    private List<String[]> filelist = new ArrayList<String[]>();
    private String name = null;
    private String data = null;
    private String label = null;
    private String result = null;
    
    //写一个输出流  
    private InputStream xmlStream;  
    
    public InputStream getXmlStream() {
        return xmlStream;
    }

    /**
     * @return the result
     */
    public String getResult() {
      return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
      this.result = result;
    }

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
  
    public String deleteTemplate() {
      if (this.name != null) {
        FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
        BusinessResult br = fb.getLeafByName(this.name);
        TemplateBean al = (TemplateBean) br.getResponseData();
        if (al != null) {
          this.label = al.getLabel();
          fb.deleteLeaf(al.get_id().toString());
        }
      }
      return ActionSupport.SUCCESS;
    }
    
    public String getxml() {
        if (this.name != null) {
            FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
            BusinessResult br = fb.getLeafByName(this.name);
            TemplateBean al = (TemplateBean) br.getResponseData();
            if (al != null) {
                String path = al.getPath();
                try {
                    FileInputStream fi = new FileInputStream(path);
                    this.xmlStream = fi;
                } catch (FileNotFoundException e) {
                    LOG.error("this exception [#0]", e.getMessage());
                }
            }
        }
        return ActionSupport.SUCCESS;
    }

    public String savexml() {

        if (this.name != null && this.data != null && this.label != null) {
            try {
                String storexmldirectory = ServerContext.getValue("storexmldirectory");
                String requestPath = null;
                if (storexmldirectory == null) {
                    String directory = System.getProperty("user.home") + File.separator + "xml";
                    storexmldirectory = directory;
                }
                if (Files.notExists(Paths.get(storexmldirectory))) {
                    Files.createDirectories(Paths.get(storexmldirectory));
                }
                requestPath = storexmldirectory + File.separator + name + ".xml";
                
                LOG.info("save xml content to this [#0] file", requestPath);
                Files.write(Paths.get(requestPath), this.data.getBytes("UTF-8"));

                // stored in template table, refer to dynamictable.js
                FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
                BusinessResult br = fb.getLeafByName(this.name);
                TemplateBean al = (TemplateBean) br.getResponseData();
                if (al != null) {
                    TemplateBean orignalT = (TemplateBean) al;
                    TemplateBean newT = (TemplateBean) orignalT.clone();
                    newT.setLabel(this.label);
                    fb.updateLeaf(orignalT, newT);
                } else {
                    TemplateBean tb = new TemplateBean();
                    tb.setName(this.name);
                    tb.setPath(requestPath);
                    tb.setLabel(this.label);
                    fb.createLeaf(tb);
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
            // find all records in the template;
            BusinessResult br = fb.getAllLeaves();
            List<TemplateBean> elements = (List<TemplateBean>) br.getResponseData();
            for (TemplateBean te : elements) {
                String[] row = new String[] { te.getLabel(), te.getName() };
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

    /**
     * aware injection of struts2
     */
    @Override
    public void setServletContext(ServletContext context) {
        this.sc = context;
    }

    public static void main(String[] args) {
        FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
        // create a leaf in the MongoDB.
        {
            TemplateBean tb = new TemplateBean();
            tb.setLabel("测试数据1");
            tb.setName("template1");
            tb.setPath("xml/template1.xml");
            fb.createLeaf(tb);
        }

        // find a leaf in the MongoDB.

        {
            BusinessResult br = fb.getLeafByName("template1");
            Object o = br.getResponseData();
            if (o != null) {
                TemplateBean find = (TemplateBean) o;
                // find a leaf by object id string in the MongoDB.
                {
                    BusinessResult br1 = fb.getLeaf(find.get_id().toString());
                    Object o1 = br1.getResponseData();
                    if (o1 != null) {
                        TemplateBean find1 = (TemplateBean) o1;
                        System.out.println(find1.getPath());
                    }
                }

                System.out.println(find.getPath());
            }
        }

        // update a leaf in the MongoDB.
        {
            BusinessResult br = fb.getLeafByName("template1");
            Object o = br.getResponseData();
            if (o != null) {
                TemplateBean orginal = (TemplateBean) o;
                TemplateBean cloneT = (TemplateBean) orginal.clone();
                cloneT.setPath("xml/template_test.xml");
                fb.updateLeaf(orginal, cloneT);
            }
        }

        // delete a leaf in the MongoDB.
        {
            BusinessResult br = fb.getLeafByName("template1");
            Object o = br.getResponseData();
            if (o != null) {
                TemplateBean find = (TemplateBean) o;
                System.out.println(find.getPath());
                fb.deleteLeaf(find.get_id().toString());
            }
        }
        
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmssSSS");
        System.out.println(sdf.format(date));
    }
}
