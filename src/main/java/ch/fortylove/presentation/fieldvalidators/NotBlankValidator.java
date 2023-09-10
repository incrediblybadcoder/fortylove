package ch.fortylove.presentation.fieldvalidators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import jakarta.annotation.Nonnull;

public class NotBlankValidator implements Validator<String> {

    @Nonnull private final String fieldName;

    public NotBlankValidator(@Nonnull final String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public ValidationResult apply(@Nonnull final String value,
                                  @Nonnull final ValueContext context) {
        return value.isBlank() ?
                ValidationResult.error(String.format("Eingabe %s ist zwingend", fieldName)) :
                ValidationResult.ok();
    }
}
