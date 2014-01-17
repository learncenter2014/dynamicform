package dao;

public class FailureDBException extends RuntimeException {

  public FailureDBException() {
    super();
  }

  public FailureDBException(String s) {
    super(s);
  }
}
