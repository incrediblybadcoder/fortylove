package ch.fortylove.presentation.views.booking.bookinggrid.events;

import ch.fortylove.presentation.views.booking.bookinggrid.BookingGrid;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public abstract class BookingGridEvent extends ComponentEvent<BookingGrid> {

    public BookingGridEvent(@Nonnull final BookingGrid source) {
        super(source, false);
    }
}
