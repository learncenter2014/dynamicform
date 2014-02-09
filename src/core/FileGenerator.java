package core;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * Created by wangronghua on 14-1-27.
 */
class FileGenerator {
  protected static Logger LOG = LoggerFactory.getLogger(FileGenerator.class);

  private static FileGenerator instance = new FileGenerator();
  private static Configuration tempCfg = new Configuration();
  private static Configuration predefinedCfg = new Configuration();

  private FileGenerator() {}

  static {
    init();
  }

  public static FileGenerator get() {
    return instance;
  }

  private static void init() {
    try {
      tempCfg.setDirectoryForTemplateLoading(new File(Constants.TEMPLATE_PATH_TEMP));
      tempCfg.setObjectWrapper(new DefaultObjectWrapper());
      tempCfg.setDefaultEncoding("UTF-8");
      tempCfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);


      predefinedCfg.setDirectoryForTemplateLoading(new File(Constants.TEMPLATE_PATH_PRE));
      predefinedCfg.setObjectWrapper(new DefaultObjectWrapper());
      predefinedCfg.setDefaultEncoding("UTF-8");
      predefinedCfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void genFile(String templateId, Object object, Writer out) throws IOException, TemplateException {
    Template temp ;
    try {
      /* Get the template */
      temp = predefinedCfg.getTemplate(templateId);
    } catch (IOException e) {
      temp = null;
    }
    if(temp == null) {
      try {
        temp = tempCfg.getTemplate(templateId);
      } catch (IOException e) {
        LOG.warn("Exception happened while trying to find template",  e);
        throw(e);
      }
    }

    try {
      temp.process(object, out);
    } catch (TemplateException e) {
      LOG.warn("Exception happened while trying to parse template", e);
      throw(e);
    }
  }
}
