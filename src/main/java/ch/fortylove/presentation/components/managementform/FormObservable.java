package ch.fortylove.presentation.components.managementform;

import jakarta.annotation.Nonnull;

public interface FormObservable<T> {

    void addFormObserver(@Nonnull final FormObserver<T> observer);
}
