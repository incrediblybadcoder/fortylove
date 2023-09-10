package ch.fortylove.presentation.views.booking.bookingcomponent.grid.events;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.presentation.views.booking.bookingcomponent.grid.BookingGrid;
import jakarta.annotation.Nonnull;

public class BookedCellClickEvent extends BookingGridEvent {

    @Nonnull private final Court court;
    @Nonnull private final Timeslot timeslot;
    @Nonnull private final Booking booking;

    public BookedCellClickEvent(@Nonnull final BookingGrid source,
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
