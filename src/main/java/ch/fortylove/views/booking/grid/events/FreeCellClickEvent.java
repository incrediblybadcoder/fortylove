package ch.fortylove.views.booking.grid.events;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.TimeSlot;
import ch.fortylove.views.booking.grid.BookingGridComponent;

import javax.annotation.Nonnull;

public class FreeCellClickEvent extends BookingGridEvent {

    @Nonnull private final Court court;
    @Nonnull private final TimeSlot timeSlot;

    public FreeCellClickEvent(@Nonnull final BookingGridComponent source,
                              @Nonnull final Court court,
                              @Nonnull final TimeSlot timeSlot) {
        super(source);
        this.court = court;
        this.timeSlot = timeSlot;
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    @Nonnull
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
}
