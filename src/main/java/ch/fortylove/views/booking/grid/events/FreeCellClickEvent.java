package ch.fortylove.views.booking.grid.events;

import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.views.booking.grid.BookingGridComponent;

import javax.annotation.Nonnull;

public class FreeCellClickEvent extends BookingGridEvent {

    @Nonnull private final CourtDTO courtDTO;
    @Nonnull private final TimeSlotDTO timeSlotDTO;

    public FreeCellClickEvent(@Nonnull final BookingGridComponent source,
                              @Nonnull final CourtDTO courtDTO,
                              @Nonnull final TimeSlotDTO timeSlotDTO) {
        super(source);
        this.courtDTO = courtDTO;
        this.timeSlotDTO = timeSlotDTO;
    }

    @Nonnull
    public CourtDTO getCourt() {
        return courtDTO;
    }

    @Nonnull
    public TimeSlotDTO getTimeSlot() {
        return timeSlotDTO;
    }
}
