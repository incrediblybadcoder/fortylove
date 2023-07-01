package ch.fortylove.views.booking.grid.events;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.views.booking.grid.BookingGridComponent;

import javax.annotation.Nonnull;

public class BookedCellClickEvent extends BookingGridEvent {

    @Nonnull private final Court court;
    @Nonnull private final Timeslot timeslot;
    @Nonnull private final Booking booking;

    public BookedCellClickEvent(@Nonnull final BookingGridComponent source,
                                @Nonnull final Court court,
                                @Nonnull final Timeslot timeslot,
                                @Nonnull final Booking booking) {
        super(source);
        this.court = court;
        this.timeslot = timeslot;
        this.booking = booking;
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    @Nonnull
    public Timeslot getTimeSlot() {
        return timeslot;
    }

    @Nonnull
    public Booking getBooking() {
        return booking;
    }
}
