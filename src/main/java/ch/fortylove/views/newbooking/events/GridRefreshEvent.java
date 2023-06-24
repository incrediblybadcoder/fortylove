package ch.fortylove.views.newbooking.events;

import ch.fortylove.views.newbooking.BookingComponent;
import com.vaadin.flow.component.ComponentEvent;

import javax.annotation.Nonnull;
import java.time.LocalDate;

public class GridRefreshEvent extends ComponentEvent<BookingComponent> {

    @Nonnull private final LocalDate date;

    public GridRefreshEvent(@Nonnull final BookingComponent source,
                            @Nonnull final LocalDate date) {
        super(source, false);
        this.date = date;
    }

    @Nonnull
    public LocalDate getDate() {
        return date;
    }
}
