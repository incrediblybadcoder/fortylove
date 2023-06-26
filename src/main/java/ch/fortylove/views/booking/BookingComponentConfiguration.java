package ch.fortylove.views.booking;

import ch.fortylove.persistence.dto.TimeSlot;

import javax.annotation.Nonnull;
import java.util.List;

public record BookingComponentConfiguration(@Nonnull List<TimeSlot> timeSlots) {
}
