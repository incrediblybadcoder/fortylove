package ch.fortylove.views.newbooking.dateselection.events;

import ch.fortylove.views.newbooking.dateselection.DateSelectionComponent;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;
import java.time.LocalDate;

public class DateChangeEvent extends ComponentEvent<DateSelectionComponent> {

    @Nonnull private final LocalDate date;

    public DateChangeEvent(@Nonnull final DateSelectionComponent source,
                           @Nonnull final LocalDate date) {
        super(source, false);
        this.date = date;
    }

    @Nonnull
    public LocalDate getDate() {
        return date;
    }
}
