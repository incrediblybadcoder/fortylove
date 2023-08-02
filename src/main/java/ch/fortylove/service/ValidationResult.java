package ch.fortylove.service;

import jakarta.annotation.Nonnull;

public class ValidationResult {

    private final boolean successful;
    @Nonnull private final String message;

    private ValidationResult(final boolean successful,
                             @Nonnull final String message) {
        this.successful = successful;
        this.message = message;
    }

    @Nonnull
    public static ValidationResult success() {
        return new ValidationResult(true, "");
    }

    @Nonnull
    public static ValidationResult failure(@Nonnull final String message) {
        return new ValidationResult(false, message);
    }

    public boolean isSuccessful() {
        return successful;
    }

    @Nonnull
    public String getMessage() {
        return message;
    }
}
