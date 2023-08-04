package ch.fortylove.util.fieldvalidators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

public class LastNameValidator {
    private static final int MAX_LAST_NAME_LENGTH = 50;
    public static ValidationResult validateLastName(String value, ValueContext context) {
        if (value.isEmpty()) {
            return ValidationResult.error("Der Nachname darf nicht leer sein");
        } else if (value.length() > MAX_LAST_NAME_LENGTH) {
            return ValidationResult.error("Der Nachname darf maximal "+ MAX_LAST_NAME_LENGTH +" Zeichen haben");
        }
        return ValidationResult.ok();
    }
}
