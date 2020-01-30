package francis.market.user.exception;

import lombok.Getter;

/**
 * Exception raised when the userid already exist in DB.
 *
 */
@Getter
public class UserAlreadyExistException extends  Exception {
    private int statusCode;
    private String message;

    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }

    public UserAlreadyExistException(int statusCode, String message) {
        super();
        this.statusCode = statusCode;
        this.message = message;
    }

    public UserAlreadyExistException(String message, Throwable t) {
        super(message, t);
    }
}
