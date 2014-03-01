package bl.beans;

import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

/**
 * Created by wangronghua on 14-3-1.
 */
@Entity(value = "patient")
public class PatientBean extends Bean{
  private String patientName;
  private String sex;
  private String age;
  private Date birthday;

}
