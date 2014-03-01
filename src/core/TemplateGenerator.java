package core;

import core.componentext.ComponentBase;
import core.componentext.FieldSetExtension;
import core.componentext.FormExtension;
import core.componentext.RowExtension;
import core.exception.XmlFileNotFoundException;
import dynamicschema.*;

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
      List<Row> rows = fieldSet.getRow();
      String dtString = genTemplate(rows.toArray(new Row[0]));

      FieldSetExtension fieldSetExtension = (FieldSetExtension)fieldSet.enhance(enhancer);
      fieldSetExtension.setInnerHTML(dtString);
      result.append(TemplateHelper.getTemplate("fieldset", fieldSetExtension));
    }
    return result.toString();
  }

  String genTemplate(Row[] rows) {
    ComponentEnhancer enhancer = new ComponentEnhancer();
    StringBuilder result = new StringBuilder();
    for(Row row: rows) {
      List<DynamicType> dynamicTypes = row.getElement();
      String dtString = genTemplate(dynamicTypes.toArray(new DynamicType[0]), row.getSize());

      RowExtension rowExtension = (RowExtension)row.enhance(enhancer);
      rowExtension.setInnerHTML(dtString);
      result.append(TemplateHelper.getTemplate("row", rowExtension));
    }
    return result.toString();
  }

  String genTemplate(DynamicType[] types, int size) {
    int resolution = Constants.TOTAL_COLUMN_SIZE/size;
    DynamicType[] dynamicTypes = new DynamicType[size];
    for(DynamicType dynamicType: types) {
      dynamicTypes[dynamicType.getPosition()] = dynamicType;
    }

    StringBuilder result = new StringBuilder();
    ComponentEnhancer enhancer = new ComponentEnhancer();
    for(DynamicType dynamicType: dynamicTypes) {
      if(dynamicType == null) {
        HashMap map = new HashMap();
        map.put("resolution",resolution);
        result.append(TemplateHelper.getTemplate("blankcell", map));
        continue;
      }
      StringBuilder type = new StringBuilder("get").append(dynamicType.getType());
      type.setCharAt(3, Character.toUpperCase(type.charAt(3)));
      try {
        Method med = DynamicType.class.getDeclaredMethod(type.toString());
        Component component = (Component)med.invoke(dynamicType);
        ComponentBase extension = component.enhance(enhancer);
        extension.setResolution(resolution);
        //todo use vistior mode to enhance the componenet
        result.append(TemplateHelper.getTemplate(dynamicType.getType().toLowerCase(), extension)) ;
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