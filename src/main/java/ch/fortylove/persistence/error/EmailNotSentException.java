package ch.fortylove.persistence.error;

public class EmailNotSentException extends RuntimeException {

    public EmailNotSentException(String message) {
        super(message);
    }

    public EmailNotSentException(String message, Throwable cause) {
        super(message, cause);
    }
}

