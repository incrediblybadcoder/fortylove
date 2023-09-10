package ch.fortylove.presentation.fieldvalidators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import jakarta.annotation.Nonnull;

public class LastNameValidator {

    private static final int MAX_LAST_NAME_LENGTH = 50;

    @Nonnull
    public static ValidationResult validateLastName(@Nonnull final String value,
                                                    @Nonnull final ValueContext context) {
        if (value.isEmpty()) {
            return ValidationResult.error("Nachname darf nicht leer sein");
        } else if (value.length() > MAX_LAST_NAME_LENGTH) {
            return ValidationResult.error("Nachname darf maximal "+ MAX_LAST_NAME_LENGTH +" Zeichen haben");
        }
        return ValidationResult.ok();
    }
}
