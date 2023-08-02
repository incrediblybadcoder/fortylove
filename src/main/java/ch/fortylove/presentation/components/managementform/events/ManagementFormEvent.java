package ch.fortylove.presentation.components.managementform.events;

import ch.fortylove.presentation.components.managementform.ManagementForm;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public abstract class ManagementFormEvent <T> extends ComponentEvent<ManagementForm<T>> {

    @Nonnull private final T item;

    protected ManagementFormEvent(@Nonnull final ManagementForm<T> source,
                                  @Nonnull final T item) {
        super(source, false);
        this.item = item;
    }

    @Nonnull
    public T getItem() {
        return item;
    }
}
