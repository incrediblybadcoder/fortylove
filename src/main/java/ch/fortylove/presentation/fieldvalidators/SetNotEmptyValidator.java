package ch.fortylove.presentation.fieldvalidators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

import java.util.Set;

public class SetNotEmptyValidator<T> implements Validator<Set<T>> {

    private final String errorMessage;

    public SetNotEmptyValidator(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public ValidationResult apply(Set<T> value, ValueContext context) {
        if (value == null || value.isEmpty()) {
            return ValidationResult.error(errorMessage);
        }
        return ValidationResult.ok();
    }
}

