package francis.market.project.exception;

/**
 * InvalidBidAmountException
 */
public class InvalidBidAmountException extends Exception {

    private int statusCode;
    private String message;

    public InvalidBidAmountException() {
        super();
    }

    public InvalidBidAmountException(String message) {
        super(message);
        this.message = message;
    }

    public InvalidBidAmountException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public InvalidBidAmountException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
