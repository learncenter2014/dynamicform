/**
 * @author gudong
 * @since Date: Mar 1, 2014
 */
package interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import util.WrappedRuntimeException;
import webapps.WebappsConstants;
import actions.UserAction;
import bl.exceptions.MiServerException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gudong
 * 
 */
public class LoginInterceptor extends AbstractInterceptor {
  protected static Logger log = LoggerFactory.getLogger(MiServerExceptionInterceptor.class);

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    if (ActionContext.getContext().getSession().get(UserAction.LOGIN_USER_SESSION_ID) == null) {
      return "login_failure";
    }
    String result;
    try {
      result = invocation.invoke();
    } catch (MiServerException e) {
      if (invocation.getAction() instanceof ActionSupport) {
        ActionSupport as = (ActionSupport) invocation.getAction();
        String errorMessage = as.getText(e.getKeyMessage(), e.getParameterMessage());
        log.error(errorMessage);
        invocation.getStack().setValue(WebappsConstants.CTX_TOKEN_ERROR_MSG_REQUEST, errorMessage);
        return as.INPUT;
      } else {
        log.error("This action exception is: {}", e);
        throw new WrappedRuntimeException(e);
      }
    }
    return result;
  }
}
