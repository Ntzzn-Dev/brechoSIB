package utils;

public class ReviewOperationException extends RuntimeException {

    public ReviewOperationException() {
        super("Revisando");
    }

    public ReviewOperationException(String msg) {
        super(msg);
    }

    public ReviewOperationException(ReviewOperationException msg) {
        super(msg);
    }
}