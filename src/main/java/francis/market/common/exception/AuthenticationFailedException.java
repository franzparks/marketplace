package francis.market.common.exception;

import lombok.Getter;

/**
 * AuthenticationFailedException
 */
@Getter
@SuppressWarnings("unused")
public class AuthenticationFailedException extends Exception {

    private int statusCode;
    private String message;

    public AuthenticationFailedException() {
        super();
    }

    public AuthenticationFailedException(String message) {
        super(message);
        this.message = message;
    }

    public AuthenticationFailedException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public AuthenticationFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
