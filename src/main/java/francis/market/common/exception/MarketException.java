package francis.market.common.exception;

import lombok.Getter;

/**
 * MarketException
 */
@Getter
public class MarketException extends Exception {

    private int statusCode;
    private String message;

    public MarketException() {
        super();
    }

    public MarketException(String message) {
        super(message);
        this.message = message;
    }

    public MarketException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public MarketException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
