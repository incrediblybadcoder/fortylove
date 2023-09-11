package ch.fortylove.presentation.fieldvalidators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import jakarta.annotation.Nonnull;

public class NameValidator implements Validator<String> {

    private static final int MAX_FIRST_NAME_LENGTH = 50;

    @Nonnull private final String fieldName;

    public NameValidator(@Nonnull final String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public ValidationResult apply(@Nonnull final String value,
                                  @Nonnull final ValueContext context) {
        if (value.isEmpty()) {
            return ValidationResult.error(String.format("%s darf nicht leer sein", fieldName));
        } else if (value.length() > MAX_FIRST_NAME_LENGTH) {
            return ValidationResult.error(String.format("%s  darf maximal %d Zeichen haben", fieldName, MAX_FIRST_NAME_LENGTH));
        }
        return ValidationResult.ok();
    }
}
