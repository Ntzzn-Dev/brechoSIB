package utils;

public class CancelOperationException extends RuntimeException {

    public CancelOperationException() {
        super("Operação cancelada");
    }

    public CancelOperationException(String msg) {
        super(msg);
    }

    public CancelOperationException(CancelOperationException msg) {
        super(msg);
    }
}