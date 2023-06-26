package ch.fortylove.views.newbooking.grid.util;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class CourtUtil {

    @Nonnull
    public static Optional<BookingDTO> getBookingForTimeSlot(@Nonnull final List<BookingDTO> bookings,
                                                          @Nonnull final TimeSlotDTO timeSlot) {
        final List<BookingDTO> filteredBookings = bookings.stream().filter(booking -> booking.getTimeSlotIndex() == timeSlot.getIndex()).toList();
        if (filteredBookings.size() > 1) {
            throw new IllegalStateException("Duplicate Booking for timeslot: " + timeSlot.getIndex());
        }

        return filteredBookings.size() == 1 ? Optional.of(filteredBookings.get(0)) : Optional.empty();
    }
}
