package timnekk.exceptions;

public class GameFlowException extends Exception {
    public GameFlowException(String message) {
        super(message);
    }

    public GameFlowException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameFlowException(Throwable cause) {
        super(cause);
    }

    public GameFlowException() {
        super();
    }
}