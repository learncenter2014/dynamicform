package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by wangronghua on 14-6-17.
 */
@Entity(value = "document")
public class DocumentBean extends Bean{

  private String tableName;
  private int type; //0:包含有子元素；1:包含无子元素
  private String code; //代码
  private String abbreviation; //缩写
  private String columnCount;

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getColumnCount() {
    return columnCount;
  }

  public void setColumnCount(String columnCount) {
    this.columnCount = columnCount;
  }

}
