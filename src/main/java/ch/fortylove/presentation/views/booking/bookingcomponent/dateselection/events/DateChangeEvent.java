package ch.fortylove.presentation.views.booking.bookingcomponent.dateselection.events;

import ch.fortylove.presentation.views.booking.bookingcomponent.dateselection.DateSelection;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public class DateChangeEvent extends ComponentEvent<DateSelection> {

    @Nonnull private final LocalDate date;

    public DateChangeEvent(@Nonnull final DateSelection source,
                           @Nonnull final LocalDate date) {
        super(source, false);
        this.date = date;
    }

    @Nonnull
    public LocalDate getDate() {
        return date;
    }
}
