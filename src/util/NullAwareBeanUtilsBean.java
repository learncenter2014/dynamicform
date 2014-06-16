package util;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 14-4-10.
 */
public class NullAwareBeanUtilsBean extends BeanUtilsBean {
    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if (value == null) return;
        super.copyProperty(dest, name, value);
    }
}
