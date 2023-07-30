package ch.fortylove.presentation.components.managementform;

import com.vaadin.flow.component.DetachNotifier;
import jakarta.annotation.Nonnull;

public interface FormObserver <T> extends DetachNotifier {

    void saveEvent(@Nonnull final T item);

    void updateEvent(@Nonnull final T item);

    void deleteEvent(@Nonnull final T item);
}
