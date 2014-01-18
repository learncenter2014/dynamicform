package util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;

/**
 * Class to represent runtime errors that are generated as a result of a non-runtime exception. This simply "wraps" the exception and returns
 * information from that exception (like the stacktrace, message, etc).
 */
public class WrappedRuntimeException extends RuntimeException {

    /** Serial version UID. */
    private static final long serialVersionUID = -4611230850443012343L;

    private Exception wrappedException = null;
    private String message = null;

    /**
     * Default Constructor.
     */
    public WrappedRuntimeException() {
        init(null, null);
    }

    /**
     * Constructor that takes the exception to be wrapped.
     */
    public WrappedRuntimeException(Exception x) {
        init(x, null);
    }

    /**
     * Constructor with no wrapped exception. Kind of makes this class meaningless, but sometimes we want to extend this class with exceptions that
     * sometimes wrap other exceptions and sometimes don't.
     */
    public WrappedRuntimeException(String message) {
        init(null, message);
    }

    /**
     * Constructor that takes the exception to be wrapped, and an extra message to be printed with the wrapped exceptions message to provide
     * additional information or context.
     */
    public WrappedRuntimeException(Exception x, String message) {
        init(x, message);
    }

    /**
     * Constructor that takes an extra message to be printed to provide additional information or context. The extra message is specified using a
     * message format with some additional arguments to use for the formatting.
     */
    public WrappedRuntimeException(String format, Object[] args) {
        String formattedMessage = null;
        try {
            if (format != null) {
                formattedMessage = MessageFormat.format(format, args);
            }
        } catch (Exception ex) {
            formattedMessage = "Internal Error while formatting error message:\n\t" + "Error message format was: " + format;
        }
        init(null, formattedMessage);
    }

    /**
     * Constructor that takes the exception to be wrapped, and an extra message to be printed with the wrapped exceptions message to provide
     * additional information or context. The extra message is specified using a message format with some additional arguments to use for the
     * formatting.
     */
    public WrappedRuntimeException(Exception x, String format, Object[] args) {
        String formattedMessage = null;
        try {
            if (format != null) {
                formattedMessage = MessageFormat.format(format, args);
            }
        } catch (Exception ex) {
            formattedMessage = "Internal Error while formatting error message:\n\t" + "Error message format was: " + format;
        }
        init(x, formattedMessage);
    }

    // Used by all constructors
    private void init(Exception x, String message) {
        wrappedException = x;
        if (message != null) {
            if (wrappedException != null)
                this.message = message + "\n" + wrappedException.getMessage();
            else
                this.message = message;
        }
    }

    /**
     * Returns the appropriate information from the wrapped exception, along with any messages added in the constructor.
     */
    @Override
    public String getMessage() {
        if (message != null)
            return message;
        if (wrappedException != null)
            return wrappedException.getMessage();
        return super.getMessage();
    }

    /**
     * Returns the appropriate information from the wrapped exception.
     */
    @Override
    public void printStackTrace() {
        if (wrappedException != null)
            wrappedException.printStackTrace();
        else
            super.printStackTrace();
    }

    /**
     * Returns the appropriate information from the wrapped exception.
     */
    @Override
    public void printStackTrace(PrintStream s) {
        if (wrappedException != null)
            wrappedException.printStackTrace(s);
        else
            super.printStackTrace(s);
    }

    /**
     * Returns the appropriate information from the wrapped exception.
     */
    @Override
    public void printStackTrace(PrintWriter s) {
        if (wrappedException != null)
            wrappedException.printStackTrace(s);
        else
            super.printStackTrace(s);
    }

    /**
     * Returns the wrapped exception itself.
     */
    public Exception getWrappedException() {
        return wrappedException;
    }
}
