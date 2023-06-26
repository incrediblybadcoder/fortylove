package ch.fortylove.views.booking.grid.events;

import ch.fortylove.views.booking.grid.BookingGridComponent;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;

public abstract class BookingGridEvent extends ComponentEvent<BookingGridComponent> {

    public BookingGridEvent(@Nonnull final BookingGridComponent source) {
        super(source, false);
    }
}
