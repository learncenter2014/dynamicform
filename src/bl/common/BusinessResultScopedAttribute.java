package bl.common;

/**
 * This class provides a container for Scoped Attributes
 */
public class BusinessResultScopedAttribute {

    /**
     * Constructor
     * 
     * @param scope
     *            one of WebappsConstants.SCOPE_APPLICATION, WebappsConstants.SCOPE_SESSION, WebappsConstants.SCOPE_REQUEST
     * @param name
     *            the attribute name
     * @param value
     *            the attribute value
     */

    public BusinessResultScopedAttribute(String scope, String name, Object value) {

        m_scope = scope;
        m_name = name;
        m_value = value;
    }

    /*
     * Get the scope.
     * 
     * @return the scope of the attribute
     */

    public String getScope() {

        return m_scope;
    }

    /**
     * Get the attribute name
     * 
     * @return the name of the attribute
     */

    public String getName() {

        return m_name;
    }

    /**
     * Get the value of the attribute.
     * 
     * @return the value of the attribute
     */

    public Object getValue() {

        return m_value;
    }

    private String m_scope;
    private String m_name;
    private Object m_value;
}
