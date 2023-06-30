package ch.fortylove.views.booking;

import ch.fortylove.persistence.dto.TimeSlotDTO;

import javax.annotation.Nonnull;
import java.util.List;

public record BookingComponentConfiguration(@Nonnull List<TimeSlotDTO> timeSlotDTOs) {
}
