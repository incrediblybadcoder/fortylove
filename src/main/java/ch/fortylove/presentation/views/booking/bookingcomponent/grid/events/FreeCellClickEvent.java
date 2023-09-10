package ch.fortylove.presentation.views.booking.bookingcomponent.grid.events;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.presentation.views.booking.bookingcomponent.grid.BookingGrid;
import jakarta.annotation.Nonnull;

public class FreeCellClickEvent extends BookingGridEvent {

    @Nonnull private final Court court;
    @Nonnull private final Timeslot timeslot;

    public FreeCellClickEvent(@Nonnull final BookingGrid source,
                              @Nonnull final Court court,
                              @Nonnull final Timeslot timeslot) {
        super(source);
        this.court = court;
        this.timeslot = timeslot;
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    @Nonnull
    public Timeslot getTimeSlot() {
        return timeslot;
    }
}
