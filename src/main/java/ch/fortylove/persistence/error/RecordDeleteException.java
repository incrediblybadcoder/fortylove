package ch.fortylove.persistence.error;

import jakarta.annotation.Nonnull;

public class RecordDeleteException extends DataBaseException {

    public RecordDeleteException() {
        super("");
    }

    public RecordDeleteException(@Nonnull final String message) {
        super(message);
    }
}
