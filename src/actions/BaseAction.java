/**
 * 
 */
package actions;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author gudong
 * 
 */
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
  private static Logger log = LoggerFactory.getLogger(BaseAction.class);
  private static final long serialVersionUID = 8845271296394980397L;
  protected final static String JSON_RESULT = "json_result";
  protected final static String FAILURE = "failure";
  private HttpServletRequest request;

  private ServletResponse response;

  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  public HttpSession getSession() {
    return request.getSession();
  }

  public void setServletResponse(HttpServletResponse response) {
    this.response = response;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public ServletResponse getResponse() {
    return response;
  }

  // 获取ip地址
  public String getIpAddress() {
    HttpServletRequest request = getRequest();
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  public String getReturnUrl() {
    HttpServletRequest request = getRequest();
    return request.getRequestURL().toString();
  }

  /**
   * 写jason
   * 
   * @param response
   * @param jason
   */
  protected void writeJson(JSON json) {
    response.setContentType("text/json; charset=utf-8");
    try {
      response.getWriter().write(json.toString());
    } catch (IOException e) {
      e.printStackTrace();
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 写jasonString
   * 
   * @param response
   * @param jasonString
   */
  protected void writeJson(String jsonString) {
    response.setContentType("text/json; charset=utf-8");
    try {
      response.getWriter().write(jsonString);
    } catch (IOException e) {
      e.printStackTrace();
      log.error(e.getMessage(), e);
    }
  }
}
