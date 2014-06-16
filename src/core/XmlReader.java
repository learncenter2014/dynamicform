package core;

import core.componentext.DFObjectFactory;
import core.exception.XmlFileNotFoundException;
import dynamicschema.Document;
import dynamicschema.Form;

import javax.naming.Context;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by wangronghua on 14-2-3.
 */
public class XmlReader {

  private File xmlFile;
  public XmlReader(String xmlFilePath) throws FileNotFoundException{
    xmlFile = new File(xmlFilePath);
    if (!xmlFile.exists()) {
      throw new XmlFileNotFoundException("xmlFile does not exists");
    }
  }

  public Document readDocument() throws JAXBException, XmlFileNotFoundException {
    if(xmlFile!=null && xmlFile.exists()) {
      JAXBContext context = JAXBContext.newInstance(Document.class);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      //unmarshaller.setProperty("com.sun.xml.bind.ObjectFactory",new DFObjectFactory());
      Document document = (Document)unmarshaller.unmarshal(xmlFile);
      return document;
    } else {
      throw new XmlFileNotFoundException("xmlFile does not exists");
    }
  }
}
