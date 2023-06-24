package ch.fortylove.views.newbooking.grid.events;

import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.views.newbooking.grid.BookingGridComponent;

import javax.annotation.Nonnull;

public class FreeCellClickEvent extends BookingGridEvent {

    @Nonnull private final CourtDTO court;
    @Nonnull private final TimeSlot timeSlot;

    public FreeCellClickEvent(@Nonnull final BookingGridComponent source,
                              @Nonnull final CourtDTO court,
                              @Nonnull final TimeSlot timeSlot) {
        super(source);
        this.court = court;
        this.timeSlot = timeSlot;
    }

    @Nonnull
    public CourtDTO getCourt() {
        return court;
    }

    @Nonnull
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
}
