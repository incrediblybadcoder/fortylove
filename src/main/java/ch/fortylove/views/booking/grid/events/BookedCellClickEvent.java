package ch.fortylove.views.booking.grid.events;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.views.booking.grid.BookingGridComponent;

import javax.annotation.Nonnull;

public class BookedCellClickEvent extends BookingGridEvent {

    @Nonnull private final CourtDTO courtDTO;
    @Nonnull private final TimeSlotDTO timeSlotDTO;
    @Nonnull private final BookingDTO booking;

    public BookedCellClickEvent(@Nonnull final BookingGridComponent source,
                                @Nonnull final CourtDTO courtDTO,
                                @Nonnull final TimeSlotDTO timeSlotDTO,
                                @Nonnull final BookingDTO booking) {
        super(source);
        this.courtDTO = courtDTO;
        this.timeSlotDTO = timeSlotDTO;
        this.booking = booking;
    }

    @Nonnull
    public CourtDTO getCourt() {
        return courtDTO;
    }

    @Nonnull
    public TimeSlotDTO getTimeSlot() {
        return timeSlotDTO;
    }

    @Nonnull
    public BookingDTO getBooking() {
        return booking;
    }
}
