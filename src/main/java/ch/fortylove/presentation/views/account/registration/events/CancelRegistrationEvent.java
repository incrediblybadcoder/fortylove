package ch.fortylove.presentation.views.account.registration.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class CancelRegistrationEvent extends ComponentEvent<Component> {

    public CancelRegistrationEvent(@Nonnull final Component source) {
        super(source, false);
    }
}
