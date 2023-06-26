package ch.fortylove.views.booking.grid.util;

import ch.fortylove.persistence.dto.Booking;
import ch.fortylove.persistence.dto.TimeSlot;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class CourtUtil {

    @Nonnull
    public static Optional<Booking> getBookingForTimeSlot(@Nonnull final List<Booking> bookings,
                                                          @Nonnull final TimeSlot timeSlot) {
        final List<Booking> filteredBookings = bookings.stream().filter(booking -> booking.getTimeSlotIndex() == timeSlot.getIndex()).toList();
        if (filteredBookings.size() > 1) {
            throw new IllegalStateException("Duplicate Booking for timeslot: " + timeSlot.getIndex());
        }

        return filteredBookings.size() == 1 ? Optional.of(filteredBookings.get(0)) : Optional.empty();
    }
}
