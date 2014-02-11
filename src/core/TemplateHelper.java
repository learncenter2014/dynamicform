package core;

import dynamicschema.Component;
import dynamicschema.Input;
import dynamicschema.Text;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangronghua on 14-1-27.
 */
public class TemplateHelper {

  public static String getTemplate (String type, Object param) {
    StringWriter result = new StringWriter();
    try {
      FileGenerator.get().genFile(type + ".ftl", param, result);
    } catch (IOException e) {
      //TODO exception
    } catch (TemplateException e) {

    }
    return result.toString();
  }

  public static boolean getTemplate (String type, Object param, Writer out) {
    boolean result = true;
    try {
      FileGenerator.get().genFile(type + ".ftl", param, out);
    } catch (IOException e) {
      //TODO exception
      result = false;
    } catch (TemplateException e) {
      result = false;
    }
    return true;
  }

  public static void main(String[] args) {
    Input input = new Text();
    input.setId("1");
    input.setLabel("woao");
    input.setValue("adasdada");
    System.out.println(getTemplate("input", input));

  }
}
