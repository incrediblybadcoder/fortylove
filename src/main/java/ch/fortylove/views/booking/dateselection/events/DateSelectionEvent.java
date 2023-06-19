package ch.fortylove.views.booking.dateselection.events;

import ch.fortylove.views.booking.dateselection.DateSelectionComponent;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;

public abstract class DateSelectionEvent extends ComponentEvent<DateSelectionComponent> {

    public DateSelectionEvent(@Nonnull final DateSelectionComponent source) {
        super(source, false);
    }
}
