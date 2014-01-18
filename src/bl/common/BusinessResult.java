package bl.common;

import java.util.ArrayList;
import java.util.List;

import webapps.WebappsConstants;

/**
 * Wrapper class to manage a response from the business tier.
 */
public class BusinessResult {

    /**
     * Default constructor.
     */
    public BusinessResult() {
        // Do nothing.
    }

    /**
     * Constructor which takes parameters.
     */
    public BusinessResult(Object data) {
        this();
        setResponseData(data);
    }

    /**
     * Get the response data.
     * 
     * @return The response data.
     */
    public Object getResponseData() {
        if (_responseData != null) {
            return _responseData;
        }
        return null;
    }

    /**
     * Set the response data.
     * 
     * @param newVal
     *            The new response data.
     */
    public void setResponseData(Object newVal) {
        _responseData = newVal;
    }

    /**
     * Get whether the response data should be stored in the servlet context.
     * 
     * @return Whether the response data should be stored in the servlet context.
     */
    public boolean getStoreInContext() {
        return _storeInContext;
    }

    /**
     * Set whether the response data should be stored in the servlet context.
     * 
     * @param newVal
     *            Whether the response data should be stored in the servlet context.
     */
    public void setStoreInContext(final boolean newVal) {
        _storeInContext = newVal;
    }

    /**
     * Get the name under which to store the data in the servlet context.
     * 
     * @return The name under which to store the data in the servlet context.
     */
    public String getNameForContext() {
        return _nameForContext;
    }

    /**
     * Set the name under which to store the data in the servlet context.
     * 
     * @param newVal
     *            The name under which to store the data in the servlet context.
     */
    public void setNameForContext(final String newVal) {
        _nameForContext = newVal;
    }

    /**
     * Get the scope in which to store the response data.
     * 
     * @return The scope in which to store the response data.
     */
    public String getScope() {
        return _scope;
    }

    /**
     * Set the scope in which to store the response data.
     * 
     * @param newVal
     *            The new scope in which to store the response data.
     */
    public void setScope(final String newVal) {
        _scope = newVal;
    }

    /**
     * Get the message list (a ResourceBundle token followed by 0 or more parameters) to be displayed to the user.
     * 
     * @return The message list.
     */
    public ArrayList getMessages() {
        return _messages;
    }

    /**
     * Set the message list (a ResourceBundle token followed by 0 or more parameters) to be displayed to the user.
     * 
     * @param newVal
     *            A new message list.
     */
    public void setMessages(final ArrayList newVal) {
        _messages = newVal;
    }

    /**
     * Get the error list (a ResourceBundle token followed by 0 or more parameters) to be displayed to the user.
     * 
     * @return The error list.
     */
    public ArrayList getErrors() {
        return _errors;
    }

    /**
     * Set the error list (a ResourceBundle token followed by 0 or more parameters) to be displayed to the user.
     * 
     * @param newVal
     *            A new error list.
     */
    public void setErrors(final ArrayList newVal) {
        _errors = newVal;
    }

    /**
     * Convenience method to set error message key with one replacement arg
     * 
     * @param key
     *            the resource bundle token
     * @param arg1
     *            a replacement argument
     */
    public void setErrors(final String key, final String arg1) {

        List<String> al = new ArrayList<String>();
        al.add(key);
        al.add(arg1);
        setErrors((ArrayList) al);
    }

    /**
     * Convenience method to set error message key with two replacement arg
     * 
     * @param key
     *            the resource bundle token
     * @param arg1
     *            a replacement argument 1
     * @param arg2
     *            a replacement argument 2
     */
    public void setErrors(final String key, final String arg1, final String arg2) {

        List<String> al = new ArrayList<String>();
        al.add(key);
        al.add(arg1);
        al.add(arg2);
        setErrors((ArrayList) al);
    }

    /**
     * Returns a true if no errors
     * 
     * @return true if operation was successful; false otherwise
     */
    public boolean success() {
        return !hasErrors();
    }

    /**
     * Add a message to the message list (a ResourceBundle token followed by 0 or more parameters).
     * 
     * @param newVal
     *            A new message to be added to the message list.
     */
    public void addMessage(final Object newVal) {
        _messages.add(newVal);
    }

    /**
     * Add an error to the error list (a ResourceBundle token)
     * 
     * @param newVal
     *            A new error to be added to the message list.
     */
    public void addError(final Object newVal) {
        _errors.add(newVal);
    }

    /**
     * Adds a warning message to the warnings list.
     * 
     * @param msg
     *            keyed message to add
     */
    public void addWarning(KeyedMessage msg) {
        warnings.add(msg);
    }

    /**
     * Add a object to be stored in the context.
     * 
     * @param context
     *            the context - WebappsConstants.SCOPE_APPLICATION, WebappsConstants.SCOPE_REQUEST, or WebappsConstants.SCOPE_SESSION
     * 
     * @param nameInContext
     *            the attribute name
     * 
     * @param value
     *            the attribute value
     */

    public void storeInContext(final String context, final String nameInContext, final Object value) {
        _scopedAttributes.add(new BusinessResultScopedAttribute(context, nameInContext, value));
    }

    /**
     * Determines if there are attributes to store in the context
     * 
     * @return true if attributes exist; false otherwise
     */
    public boolean getStoreInContextList() {
        return _scopedAttributes.size() > 0;
    }

    /**
     * Get the scoped attribute list
     * 
     * @return The scoped attribute list
     */
    public ArrayList getScopedAttributes() {
        return _scopedAttributes;
    }

    /**
     * Return whether the result has a message list.
     * 
     * @return Whether the result has a message list.
     */
    public boolean hasMessages() {
        return _messages != null && _messages.size() > 0;
    }

    /**
     * Return whether the result has an error list
     * 
     * @return Whether the result has a message list.
     */
    public boolean hasErrors() {
        return _errors != null && _errors.size() > 0;
    }

    /**
     * Returns whether or not the result has a list of warnings.
     * 
     * @return <code>true</code> if warnings exist; <code>false</code> otherwise
     */
    public boolean hasWarnings() {
        return warnings != null && warnings.size() > 0;
    }

    /**
     * Get dispatch target, used to re-route control to a non-default location.
     * 
     * @return Dispatch target.
     */
    public String getDispatchTarget() {
        return _dispatchTarget;
    }

    /**
     * Set dispatch target, used to re-route control to a non-default location.
     * 
     * @param newVal
     *            New dispatch target.
     */
    public void setDispatchTarget(final String newVal) {
        _dispatchTarget = newVal;
    }

    /**
     * Get whether the dispatch target is specified as a full path or a logical name (e.g. ActionForward).
     * 
     * @return Whether the dispatch target is specified as a full path or a logical name.
     */
    public boolean getDispatchIsPath() {
        return _dispatchIsPath;
    }

    /**
     * Set whether the dispatch target is specified as a full path or a logical name (e.g. ActionForward).
     * 
     * @param newVal
     *            Whether the dispatch target is specified as a full path or a logical name.
     */
    public void setDispatchIsPath(final boolean newVal) {
        _dispatchIsPath = newVal;
    }

    /**
     * Get whether to redirect to the dispatch target
     * 
     * @return Whether to redirect or forward
     */
    public boolean getRedirect() {
        return _redirect;
    }

    /**
     * Set whether to redirect to the dispatch target
     * 
     * @param newVal
     *            Whether to redirect
     */
    public void setRedirect(final boolean newVal) {
        _redirect = newVal;
    }

    /**
     * Gets a list of <code>KeyedMessage</code> instances that represent warning messages.
     * 
     * @return list of <code>KeyedMessage</code>
     */
    public List<KeyedMessage> getWarnings() {
        return warnings;
    }

    /** The response data. */
    private Object _responseData = null;

    /** Whether the response data should be stored in the servlet context. */
    private boolean _storeInContext = false;

    /** The name under which to store the data in the servlet context. */
    private String _nameForContext = null;

    /** The scope in which to store the response data. */
    private String _scope = WebappsConstants.SCOPE_REQUEST;

    /**
     * A message list (a ResourceBundle token followed by 0 or more parameters) to be displayed to the user.
     */
    private ArrayList _messages = new ArrayList();

    /**
     * An error list (a ResourceBundle token followed by 0 or more parameters) to be displayed to the user.
     */
    private ArrayList _errors = new ArrayList();

    /**
     * A list of <code>KeyedMessage</code> instances that represent warning messages.
     * 
     * This could also potentially be implemented using a HashSet for uniqueness, but for now, this probably gives more flexibility in case multiple
     * of the same key being added to the list.
     */
    private List<KeyedMessage> warnings = new ArrayList<KeyedMessage>();

    /** Dispatch target, used to re-route control to a non-default location. */
    private String _dispatchTarget = null;

    /**
     * Whether the dispatch target is specified as a full path or a logical name (e.g. ActionForward).
     */
    private boolean _dispatchIsPath = false;

    /**
     * Whether top forward or redirect to the dispatch target
     */
    private boolean _redirect = false;

    /**
     * A list of BusinessResultScopedAttribute objects
     */
    private ArrayList _scopedAttributes = new ArrayList();

    /** Message separator for multi-value messages */
    private static final String MESSAGE_SEPARATOR = ", ";

}
