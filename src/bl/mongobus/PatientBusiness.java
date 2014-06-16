package bl.mongobus;

import bl.beans.PatientBean;
import bl.common.BeanContext;
import bl.common.BusinessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wangronghua on 14-3-3.
 */
public class PatientBusiness extends MongoCommonBusiness<BeanContext, PatientBean> {
  private static Logger log = LoggerFactory.getLogger(PatientBusiness.class);

  public PatientBusiness() {
    this.dbName = "form";
    this.clazz = PatientBean.class;
  }

  @Override
  public BusinessResult getAllLeaves() {
    BusinessResult result = super.getAllLeaves();
    if(null != result.getResponseData()) {
      List<PatientBean> patientBeanList = (List<PatientBean>) result.getResponseData();
      for(PatientBean patient: patientBeanList) {
        Date birthDay = patient.getBirthday();
        Calendar cal = Calendar.getInstance();
        int thisYear = cal.get(Calendar.YEAR);
        cal.setTime(birthDay);
        int thatYear = cal.get(Calendar.YEAR);
        patient.setAge(thisYear - thatYear);
      }
    }

    return result;
  }
}
