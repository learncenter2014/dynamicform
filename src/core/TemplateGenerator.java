package core;

import core.componentext.FieldSetExtension;
import core.componentext.FormExtension;
import core.exception.XmlFileNotFoundException;
import dynamicschema.Component;
import dynamicschema.DynamicType;
import dynamicschema.FieldSet;
import dynamicschema.Form;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangronghua on 14-1-27.
 */
public class TemplateGenerator {

  public boolean genTemplate(String xmlFilePath, String templateId) {
    File template = new File(Constants.TEMPLATE_PATH_TEMP + "/" + templateId);
    Writer templateWriter = null;
    try {
      templateWriter = new OutputStreamWriter(new FileOutputStream(template), "UTF-8");
      XmlReader reader = new XmlReader(xmlFilePath);
      Form form = reader.readForm();
      templateWriter.write(this.genTemplate(form));
      templateWriter.close();
    } catch (XmlFileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
      //todo exception
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return true;
  }

  String genTemplate(Form form) {
    ComponentEnhancer enhancer = new ComponentEnhancer();
    FormExtension formExt = (FormExtension)(form.enhance(enhancer));
    List<FieldSet> fieldSets = form.getFieldset();
    formExt.setInnerHTML(genTemplate(fieldSets.toArray(new FieldSet[0])));
    return TemplateHelper.getTemplate("form", formExt);
  }

  String genTemplate(FieldSet[] fieldSets) {
    ComponentEnhancer enhancer = new ComponentEnhancer();
    StringBuilder result = new StringBuilder();
    for(FieldSet fieldSet: fieldSets) {
      List<DynamicType> dynamicTypes = fieldSet.getElement();
      String dtString = genTemplate(dynamicTypes.toArray(new DynamicType[0]));

      FieldSetExtension fieldSetExtension = (FieldSetExtension)fieldSet.enhance(enhancer);
      fieldSetExtension.setInnerHTML(dtString);
      result.append(TemplateHelper.getTemplate("fieldset", fieldSetExtension));
    }
    return result.toString();
  }

  String genTemplate(DynamicType[] dynamicTypes) {
    StringBuilder result = new StringBuilder();
    ComponentEnhancer enhancer = new ComponentEnhancer();
    for(DynamicType dynamicType: dynamicTypes) {
      StringBuilder type = new StringBuilder("get").append(dynamicType.getType());
      type.setCharAt(3, Character.toUpperCase(type.charAt(3)));
      try {
        Method med = DynamicType.class.getDeclaredMethod(type.toString());
        Component component = (Component)med.invoke(dynamicType);
        //todo use vistior mode to enhance the componenet
        result.append(TemplateHelper.getTemplate(dynamicType.getType().toLowerCase(), component.enhance(enhancer))) ;
      } catch (NoSuchMethodException e) {
        //todo LOG.error();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }

    }
    return result.toString();
  }

}