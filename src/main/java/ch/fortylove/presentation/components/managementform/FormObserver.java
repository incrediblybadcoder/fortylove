package ch.fortylove.presentation.components.managementform;

import jakarta.annotation.Nonnull;

public interface FormObserver <T> {

    void saveEvent(@Nonnull final T item);

    void updateEvent(@Nonnull final T item);

    void deleteEvent(@Nonnull final T item);
}
