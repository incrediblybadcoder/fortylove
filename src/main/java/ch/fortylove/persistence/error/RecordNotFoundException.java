package ch.fortylove.persistence.error;

import jakarta.annotation.Nonnull;

public class RecordNotFoundException extends DataBaseException {

    public RecordNotFoundException() {
        super("");
    }

    public RecordNotFoundException(@Nonnull final String message) {
        super(message);
    }

    public RecordNotFoundException(@Nonnull final Object object) {
        super("Record not found: " + object);
    }
}
