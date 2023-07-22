package ch.fortylove.persistence.error;

import javax.annotation.Nonnull;

public abstract class DataBaseException extends RuntimeException {

    @Nonnull final String message;

    public DataBaseException(@Nonnull final String message) {
        this.message = message;
    }

    @Override
    @Nonnull
    public String getMessage() {
        return message;
    }
}
