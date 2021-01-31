package fi.cdfdb.exception;

public class UnrecoverableCFException extends RuntimeException {

    public UnrecoverableCFException(String message) {
        super(message);
    }

    public UnrecoverableCFException(String message, Exception rootCause) {
        super(message, rootCause);
    }
}
