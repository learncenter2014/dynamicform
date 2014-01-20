package bl.exceptions;

import util.WrappedRuntimeException;

public class FailureInitialClassException extends WrappedRuntimeException {

    public FailureInitialClassException() {
        super();
    }

    public FailureInitialClassException(String s) {
        super(s);
    }

    public FailureInitialClassException(Exception ex) {
        super(ex);
    }
}
