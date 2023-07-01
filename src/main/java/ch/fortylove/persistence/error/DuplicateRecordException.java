package ch.fortylove.persistence.error;

import javax.annotation.Nonnull;

public class DuplicateRecordException extends DataBaseException {

    public DuplicateRecordException() {
        super("");
    }

    public DuplicateRecordException(@Nonnull final String message) {
        super(message);
    }

    public DuplicateRecordException(@Nonnull final Object object) {
        super("Duplicate record: " + object);
    }
}
