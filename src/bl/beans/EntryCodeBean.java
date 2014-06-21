package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by wangronghua on 14-6-21.
 */
@Entity(value = "entry_code")
public class EntryCodeBean extends Bean {

  private String entryId;
  private String abbreviation;//英文缩写
  private String key;
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getEntryId() {
    return entryId;
  }

  public void setEntryId(String entryId) {
    this.entryId = entryId;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

}
