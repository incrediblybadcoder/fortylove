package ch.fortylove.service.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class DatabaseResult <T> {

    @Nullable private final T data;
    @Nonnull private final String message;

    private DatabaseResult(@Nullable final T data,
                          @Nonnull final String message) {
        this.data = data;
        this.message = message;
    }

    public DatabaseResult(@Nonnull final T data) {
        this(data, "");
    }

    public DatabaseResult(@Nonnull final String message) {
        this(null, message);
    }

    @Nullable
    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }

    @Nonnull
    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return data != null;
    }
}
