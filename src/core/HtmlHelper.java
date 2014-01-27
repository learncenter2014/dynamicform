package core;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangronghua on 14-1-27.
 */
public class HtmlHelper {

  public static final String INPUT_TEMPLATE = "input.ftl";
  public static final String CHECKBOX_TEMPLATE = "checkbox.ftl";
  public static final String SELECT_TEMPLATE = "selethis.getClass().getClassLoader().getResource(\"input.ftl\")ct.ftl";
  public static final String TEXTAREA_TEMPLATE = "textarea.ftl";

  public static String getHtmlForInput (Map paraMap) {
    StringWriter result = new StringWriter();
    try {
      FileGenerator.get().genFile(INPUT_TEMPLATE, paraMap, result);
    } catch (IOException e) {

    } catch (TemplateException e) {

    }
    return result.toString();
  }

  public static void main(String[] args) {
    Map paraMap = new HashMap();
    paraMap.put("elementId", 1);
    paraMap.put("name", "test");
    paraMap.put("label", "我操Test");
    System.out.println(getHtmlForInput(paraMap));

  }
}
