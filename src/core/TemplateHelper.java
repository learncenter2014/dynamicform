package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by wangronghua on 14-1-27.
 */
public class TemplateHelper {
    protected static Logger LOG = LoggerFactory.getLogger(TemplateHelper.class);

    private static TemplateHelper instance = new TemplateHelper();
    private static Configuration tempCfg = new Configuration();
    private static Configuration predefinedCfg = new Configuration();

    private TemplateHelper() {}

    static {
        init();
    }

    public static TemplateHelper get() {
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

    public String getTemplate (EntryType type, Object param) {
        return this.getTemplate(type.getName(), param);
    }

    public String getTemplate (String templateName, Object param) {
        StringWriter result = new StringWriter();
        try {
            this.genFile(templateName + ".ftl", param, result);
        } catch (IOException e) {
            //TODO exception
        } catch (TemplateException e) {

        }
        return result.toString();
    }
}
