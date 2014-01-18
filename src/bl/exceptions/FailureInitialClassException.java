package bl.exceptions;

public class FailureInitialClassException extends RuntimeException {

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
