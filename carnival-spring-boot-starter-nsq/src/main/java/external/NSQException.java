package external;

public class NSQException extends RuntimeException {

    public NSQException(String message) {
        super(message);
    }

    public NSQException(String message, Throwable cause) {
        super(message, cause);
    }

}
