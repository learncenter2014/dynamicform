package jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dynamicschema.Form;

public class TestJaxb {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testText() {
    File file = new File("testresources/dynamicform.xml");
    try {
      JAXBContext context = JAXBContext.newInstance(Form.class);
      Unmarshaller us = context.createUnmarshaller();
      Form form = (Form) us.unmarshal(file);
      form.getFieldset().get(0);
    } catch (JAXBException e) {
      e.printStackTrace();
    }

  }

}
