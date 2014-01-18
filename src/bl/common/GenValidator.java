package bl.common;

/**
 * we will use template behavior to validate logical business
 * 
 * @author peter
 * 
 */
public interface GenValidator {
    public static enum ActionOperation {
        CREATE, DELETE, UPDATE
    };

    public BusinessResult validate(ActionOperation action, BeanContext origBean, BeanContext leafBean);

}
