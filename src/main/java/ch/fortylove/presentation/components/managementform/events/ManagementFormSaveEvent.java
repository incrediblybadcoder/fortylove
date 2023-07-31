package ch.fortylove.presentation.components.managementform.events;

import ch.fortylove.presentation.components.managementform.ManagementForm;
import jakarta.annotation.Nonnull;

public class ManagementFormSaveEvent<T> extends ManagementFormEvent<T> {

    public ManagementFormSaveEvent(@Nonnull final ManagementForm<T> source,
                                   @Nonnull final T item) {
        super(source, item);
    }
}
