package ch.fortylove.views.newbooking;

import ch.fortylove.persistence.entity.settings.TimeSlot;

import javax.annotation.Nonnull;
import java.util.List;

public record BookingComponentConfiguration(@Nonnull List<TimeSlot> timeSlots) {
}
