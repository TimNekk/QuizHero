package timnekk.exceptions;

public final class CreatingQuestionProviderException extends RuntimeException {
    public CreatingQuestionProviderException(String message) {
        super(message);
    }

    public CreatingQuestionProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreatingQuestionProviderException(Throwable cause) {
        super(cause);
    }

    public CreatingQuestionProviderException() {
        super();
    }
}