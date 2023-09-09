package ch.fortylove.presentation.fieldvalidators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import jakarta.annotation.Nonnull;

public class FirstNameValidator {

    private static final int MAX_FIRST_NAME_LENGTH = 50;

    @Nonnull
    public static ValidationResult validateFirstName(@Nonnull final String value,
                                                     @Nonnull final ValueContext context) {
        if (value.isEmpty()) {
            return ValidationResult.error("Der Vorname darf nicht leer sein");
        } else if (value.length() > MAX_FIRST_NAME_LENGTH) {
            return ValidationResult.error("Der Vorname darf maximal "+ MAX_FIRST_NAME_LENGTH + " Zeichen haben");
        }
        return ValidationResult.ok();
    }
}
