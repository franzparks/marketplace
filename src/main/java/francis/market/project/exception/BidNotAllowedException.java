package francis.market.project.exception;

/**
 * BidNotAllowedException
 */
public class BidNotAllowedException extends Exception {
    private int statusCode;
    private String message;

    public BidNotAllowedException() {
        super();
    }

    public BidNotAllowedException(String message) {
        super(message);
        this.message = message;
    }

    public BidNotAllowedException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public BidNotAllowedException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
