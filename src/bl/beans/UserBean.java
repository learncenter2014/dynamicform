package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * @author gudong
 * 
 */
@Entity(value = "user")
public class UserBean extends Bean {
  public static final int LOCK = 1;
  public static final int UN_LOCK = 0;

  private int sex; // 1=Male,2=Female
  private int lock; // 1=lock,0=Unlock
  private String password;
  private String address;
  private String qq;
  private String email;
  private String cellPhone;
  private String wechat;

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  public String getWechat() {
    return wechat;
  }

  public void setWechat(String wechat) {
    this.wechat = wechat;
  }

  public int getLock() {
    return lock;
  }

  public void setLock(int lock) {
    this.lock = lock;
  }

}
