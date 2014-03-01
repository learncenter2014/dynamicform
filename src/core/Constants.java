package core;

/**
 * Created by wangronghua on 14-2-3.
 */
public class Constants {

  public static final String ROOT_PATH = Constants.class.getClassLoader().getResource("/").getPath();

  public static final String TEMPLATE_PATH_PRE = ROOT_PATH + "template/predefined";
  public static final String TEMPLATE_PATH_TEMP = ROOT_PATH + "template/temp";

  public static final String PROPERTY_SPLIT = ";"; 
  public static final String KEY_VALUE_SPLIT = "=";

  public  static  final int TOTAL_COLUMN_SIZE = 12;
}
