package interceptor;

import util.WrappedRuntimeException;
import webapps.WebappsConstants;
import bl.exceptions.MiServerException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * this Interceptor mainly focus on business exception that is inherited with {@link bl.exceptions.MiServerException}
 * 
 * @author peter
 * 
 */
public class MiServerExceptionInterceptor extends AbstractInterceptor {
    protected static Logger LOG = LoggerFactory.getLogger(MiServerExceptionInterceptor.class);
    private static final long serialVersionUID = 6781679050585317814L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result;
        try {
            result = invocation.invoke();
        } catch (MiServerException e) {
            if (invocation.getAction() instanceof ActionSupport) {
                ActionSupport as = (ActionSupport) invocation.getAction();
                String errorMessage = as.getText(e.getKeyMessage(), e.getParameterMessage());
                LOG.error(errorMessage);
                invocation.getStack().setValue(WebappsConstants.CTX_TOKEN_ERROR_MSG_REQUEST, errorMessage);
                return as.INPUT;
            } else {
                LOG.error("This action exception is: #0", e);
                throw new WrappedRuntimeException(e);
            }
        }
        return result;
    }

}
