package timnekk.exceptions;

public final class GettingQuestionException extends Exception {
    public GettingQuestionException(String message) {
        super(message);
    }

    public GettingQuestionException(String message, Throwable cause) {
        super(message, cause);
    }

    public GettingQuestionException(Throwable cause) {
        super(cause);
    }

    public GettingQuestionException() {
        super();
    }
}