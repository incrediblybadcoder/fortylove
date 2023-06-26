package ch.fortylove.views.newbooking;

import ch.fortylove.persistence.dto.TimeSlotDTO;

import javax.annotation.Nonnull;
import java.util.List;

public record BookingComponentConfiguration(@Nonnull List<TimeSlotDTO> timeSlots) {
}
