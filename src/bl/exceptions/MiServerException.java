package bl.exceptions;

import util.WrappedRuntimeException;

/**
 * This class is the base class of all business exceptions generated within the Server and support i18n function.
 */
public class MiServerException extends WrappedRuntimeException {

    /** Serial version UID. */
    private static final long serialVersionUID = -5735009722980521069L;

    private String keyMessage = null;

    private String[] parameterMessage = null;

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }

    public String[] getParameterMessage() {
        return parameterMessage;
    }

    public void setParameterMessage(String[] parameterMessage) {
        this.parameterMessage = parameterMessage;
    }

    /** Constructor that takes a message key and args */
    public MiServerException(String key, String... args) {
        this.keyMessage = key;
        this.parameterMessage = args;
    }

    /****
     **** Exceptions related to communication with the MI Agent.
     ****/

    /** Wrapped version of java.rmi.RemoteException. */
    public static class Remote extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = -3129151581224652740L;

        /** Constructor that takes a message key and args */
        public Remote(String key, String... args) {
            super(key, args);
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

        /** Constructor that takes a message key and args */
        public NotFound(String key, String... args) {
            super(key, args);
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

        /** Constructor that takes a message key and args */
        public JarBuild(String key, String... args) {
            super(key, args);
        }
    }

    /** Can't copy properties using BeanUtils. */
    public static class BeanUtils extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = -6929545019974324509L;

        /** Constructor that takes a message key and args */
        public BeanUtils(String key, String... args) {
            super(key, args);
        }
    }

    /** App Type mismatch */
    public static class AppTypeMisMatch extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = 6293240310065660459L;

        /** Constructor that takes a message key and args */
        public AppTypeMisMatch(String key, String... args) {
            super(key, args);
        }
    }

    /** Other stuff. */
    public static class General extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = 7229486567107308323L;

        /** Constructor that takes a message key and args */
        public General(String key, String... args) {
            super(key, args);
        }
    }

    /****
     **** Not Yet Implemented.
     ***/
    public static class NYI extends MiServerException {
        /** Serial version UID. */
        private static final long serialVersionUID = -1495378543164641332L;

        /** Constructor that takes a message key and args */
        public NYI(String key, String... args) {
            super(key, args);
        }
    }

}
