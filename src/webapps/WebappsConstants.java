package webapps;

/**
 * Common constants used in web applications.
 */
public interface WebappsConstants {

    /** Constants for the various servlet scopes. */
    public static final String SCOPE_APPLICATION = "Application";
    public static final String SCOPE_SESSION = "Session";
    public static final String SCOPE_REQUEST = "Request";

    /** Tokens for storing items in the servlet context. */
    public static final String CTX_TOKEN_WEBAPP_SESSION = "WEBAPP_SESSION_CONTEXT";
    public static final String CTX_TOKEN_WEBAPP_TIMEDOUTSESSION = "WEBAPP_TIMEDOUT_SESSION";
    public static final String CTX_TOKEN_ERROR_MSG_REQUEST = "ERROR_MSG";
    public static final String CTX_TOKEN_WARNING_MSG_REQUEST = "WARNING_MSG";
    public static final String CTX_TOKEN_STATUS_MSG_REQUEST = "STATUS_MSG";
    public static final String CTX_TOKEN_ERROR_MSG_SESSION = "ERROR_MSG_SESSION";
    public static final String CTX_TOKEN_WARNING_MSG_SESSION = "WARNING_MSG_SESSION";
    public static final String CTX_TOKEN_STATUS_MSG_SESSION = "STATUS_MSG_SESSION";
    public static final String CTX_TOKEN_WARNING_STATUS_MSG_SESSION = "WARNING_STATUS_MSG_SESSION";
}
