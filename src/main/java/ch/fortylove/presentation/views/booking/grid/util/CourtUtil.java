package ch.fortylove.presentation.views.booking.grid.util;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Timeslot;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CourtUtil {

    @Nonnull
    public static Optional<Booking> getBookingForTimeSlot(@Nonnull final Set<Booking> bookings,
                                                          @Nonnull final Timeslot timeslot) {
        final List<Booking> filteredBookings = bookings.stream().filter(booking -> booking.getTimeslot().equals(timeslot)).toList();
        if (filteredBookings.size() > 1) {
            throw new IllegalStateException("Duplicate Booking for timeslot: " + timeslot.getIndex());
        }

        return filteredBookings.size() == 1 ? Optional.of(filteredBookings.get(0)) : Optional.empty();
    }
}
