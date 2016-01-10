package google.errors;

import org.apache.log4j.Logger;

/**
 * B³¹d aplikacji
 * Created by Mag.
 */
public class AppException extends RuntimeException {

    private final static Logger logger = Logger.getLogger(AppException.class);

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(
                message + ", couse by" + cause.getClass().getSimpleName() + " " + cause.getMessage(),
                cause);
    }
}
