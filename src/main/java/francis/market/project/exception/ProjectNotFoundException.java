package francis.market.project.exception;

/**
 * Exception occurred when the request project id not found in db.
 *
 */
public class ProjectNotFoundException extends Exception {
    private int statusCode;
    private String message;

    public ProjectNotFoundException() {
        super();
    }

    public ProjectNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public ProjectNotFoundException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
