package ch.fortylove.views.newbooking.grid.events;

import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.views.newbooking.grid.BookingGridComponent;

import javax.annotation.Nonnull;

public class FreeCellClickEvent extends BookingGridEvent {

    @Nonnull private final CourtDTO court;
    @Nonnull private final TimeSlotDTO timeSlot;

    public FreeCellClickEvent(@Nonnull final BookingGridComponent source,
                              @Nonnull final CourtDTO court,
                              @Nonnull final TimeSlotDTO timeSlot) {
        super(source);
        this.court = court;
        this.timeSlot = timeSlot;
    }

    @Nonnull
    public CourtDTO getCourt() {
        return court;
    }

    @Nonnull
    public TimeSlotDTO getTimeSlot() {
        return timeSlot;
    }
}
