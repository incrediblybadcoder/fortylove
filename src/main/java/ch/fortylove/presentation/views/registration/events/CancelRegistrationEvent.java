package ch.fortylove.presentation.views.registration.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class CancelRegistrationEvent extends ComponentEvent<Component> {
    public CancelRegistrationEvent(final Component source) {
        super(source, false);
    }
}
