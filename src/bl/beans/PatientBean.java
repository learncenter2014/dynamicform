package bl.beans;

import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

/**
 * Created by wangronghua on 14-3-1.
 */
@Entity(value = "patient")
public class PatientBean extends Bean{
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getHomephone() {
    return homephone;
  }

  public void setHomephone(String homephone) {
    this.homephone = homephone;
  }

  public String getCellphone() {
    return cellphone;
  }

  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public String getPatientKey() {
    return patientKey;
  }

  public void setPatientKey(String patientKey) {
    this.patientKey = patientKey;
  }

  private String patientKey;
  private String patientName;
  private int sex;
  private Integer age;
  private Date birthday;
  private String cellphone;
  private String homephone;
  private String address;
}
