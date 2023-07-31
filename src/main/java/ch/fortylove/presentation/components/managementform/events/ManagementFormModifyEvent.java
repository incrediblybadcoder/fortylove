package ch.fortylove.presentation.components.managementform.events;

import ch.fortylove.presentation.components.managementform.ManagementForm;
import jakarta.annotation.Nonnull;

public class ManagementFormModifyEvent<T> extends ManagementFormEvent<T> {

    public ManagementFormModifyEvent(@Nonnull final ManagementForm<T> source,
                                     @Nonnull final T item) {
        super(source, item);
    }
}
