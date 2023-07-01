package ch.fortylove.views.booking;

import ch.fortylove.persistence.entity.Timeslot;

import javax.annotation.Nonnull;
import java.util.List;

public record BookingComponentConfiguration(@Nonnull List<Timeslot> timeslots) {
}
