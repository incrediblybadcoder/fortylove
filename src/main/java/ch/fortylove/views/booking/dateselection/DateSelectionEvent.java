package ch.fortylove.views.booking.dateselection;

import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;

public abstract class DateSelectionEvent extends ComponentEvent<DateSelectionComponent> {

    protected DateSelectionEvent(@Nonnull final DateSelectionComponent source) {
        super(source, false);
    }
}
