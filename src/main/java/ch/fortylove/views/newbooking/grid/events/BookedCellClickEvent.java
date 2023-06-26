package ch.fortylove.views.newbooking.grid.events;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.views.newbooking.grid.BookingGridComponent;

import javax.annotation.Nonnull;

public class BookedCellClickEvent extends BookingGridEvent {

    @Nonnull private final CourtDTO court;
    @Nonnull private final TimeSlotDTO timeSlot;
    @Nonnull private final BookingDTO booking;

    public BookedCellClickEvent(@Nonnull final BookingGridComponent source,
                                @Nonnull final CourtDTO court,
                                @Nonnull final TimeSlotDTO timeSlot,
                                @Nonnull final BookingDTO booking) {
        super(source);
        this.court = court;
        this.timeSlot = timeSlot;
        this.booking = booking;
    }

    @Nonnull
    public CourtDTO getCourt() {
        return court;
    }

    @Nonnull
    public TimeSlotDTO getTimeSlot() {
        return timeSlot;
    }

    @Nonnull
    public BookingDTO getBooking() {
        return booking;
    }
}
