package ch.fortylove.views.booking.grid.events;

import ch.fortylove.persistence.dto.Booking;
import ch.fortylove.persistence.dto.Court;
import ch.fortylove.persistence.dto.TimeSlot;
import ch.fortylove.views.booking.grid.BookingGridComponent;

import javax.annotation.Nonnull;

public class BookedCellClickEvent extends BookingGridEvent {

    @Nonnull private final Court court;
    @Nonnull private final TimeSlot timeSlot;
    @Nonnull private final Booking booking;

    public BookedCellClickEvent(@Nonnull final BookingGridComponent source,
                                @Nonnull final Court court,
                                @Nonnull final TimeSlot timeSlot,
                                @Nonnull final Booking booking) {
        super(source);
        this.court = court;
        this.timeSlot = timeSlot;
        this.booking = booking;
    }

    @Nonnull
    public Court getCourt() {
        return court;
    }

    @Nonnull
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Nonnull
    public Booking getBooking() {
        return booking;
    }
}
