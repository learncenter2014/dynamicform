package core.exception;

import java.io.FileNotFoundException;

/**
 * Created by wangronghua on 14-2-8.
 */
public class XmlFileNotFoundException extends FileNotFoundException{

  public XmlFileNotFoundException(String ex) {
    super(ex);
  }
}
