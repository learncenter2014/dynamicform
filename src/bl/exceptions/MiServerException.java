package bl.exceptions;

import java.rmi.RemoteException;

import util.WrappedRuntimeException;

/**
 * This class is the base class of all exceptions generated within the Server.
 */
public class MiServerException extends WrappedRuntimeException {

    /** Serial version UID. */
    private static final long serialVersionUID = -5735009722980521069L;

    /** Default Constructor. */
    public MiServerException() {
        super();
    }

    /** Constructor that takes an exception to be wrapped. */
    public MiServerException(Exception e) {
        super(e);
    }

    /** Constructor that takes message text. */
    public MiServerException(String msg) {
        super(msg);
    }

    /** Constructor that takes an exception and message text */
    public MiServerException(Exception e, String msg) {
        super(e, msg);
    }

    /** Constructor that takes a message format and args */
    public MiServerException(String fmt, Object[] args) {
        super(fmt, args);
    }

    /** Constructor that takes an exception, message format, and args */
    public MiServerException(Exception e, String fmt, Object[] args) {
        super(e, fmt, args);
    }

    /****
     **** Exceptions related to communication with the MI Agent.
     ****/

    /** Wrapped version of java.rmi.RemoteException. */
    public static class Remote extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = -3129151581224652740L;

        public Remote(String msg, RemoteException e) {
            super(e, msg);
        }
    }

    /****
     **** Business layer exceptions.
     ****/

    /** Objects that are not found, when they should be there. */
    public static class NotFound extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = -9043059579107449491L;

        public NotFound(String msg) {
            super("Object not found: {0}", new String[] { msg });
        }
    }

    /** Objects that are conflicted, when they are modified by other user. */
    public static class Conflicted extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = -3641115916462093715L;

        public Conflicted(String msg) {
            super("Object conflicted: {0}", new String[] { msg });
        }
    }

    /** Problems when building the jar file. */
    public static class JarBuild extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = 480220348293948275L;

        public JarBuild(Exception e) {
            super(e);
        }
    }

    /** Can't copy properties using BeanUtils. */
    public static class BeanUtils extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = -6929545019974324509L;

        public BeanUtils(Exception e) {
            super(e);
        }
    }

    /** App Type mismatch */
    public static class AppTypeMisMatch extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = 6293240310065660459L;

        public AppTypeMisMatch() {
            super("AppTypeMisMatch");
        }
    }

    /** Other stuff. */
    public static class General extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = 7229486567107308323L;

        public General(String msg) {
            super(msg);
        }

        public General(Exception e) {
            super(e);
        }

        public General(String msg, Exception e) {
            super(e, msg);
        }
    }

    /****
     **** Not Yet Implemented.
     ***/
    public static class NYI extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = -1495378543164641332L;

        public NYI(String msg) {
            super(msg);
        }
    }

}
