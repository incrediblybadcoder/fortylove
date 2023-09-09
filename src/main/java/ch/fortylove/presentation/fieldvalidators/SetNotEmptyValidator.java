package ch.fortylove.presentation.fieldvalidators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Set;

public class SetNotEmptyValidator<T> implements Validator<Set<T>> {

    @Nonnull private final String errorMessage;

    public SetNotEmptyValidator(@Nonnull final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public ValidationResult apply(@Nullable final Set<T> value,
                                  @Nonnull final ValueContext context) {
        if (value == null || value.isEmpty()) {
            return ValidationResult.error(errorMessage);
        }
        return ValidationResult.ok();
    }
}

