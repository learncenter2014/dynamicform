package dao.exceptions;

import util.WrappedRuntimeException;

public class FailureDBException extends WrappedRuntimeException {

    public FailureDBException() {
        super();
    }

    public FailureDBException(String s) {
        super(s);
    }
}
