package ch.fortylove.views.booking.grid.util;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class CourtUtil {

    @Nonnull
    public static Optional<BookingDTO> getBookingForTimeSlot(@Nonnull final List<BookingDTO> bookingDTOs,
                                                             @Nonnull final TimeSlotDTO timeSlotDTO) {
        final List<BookingDTO> filteredBookings = bookingDTOs.stream().filter(booking -> booking.getTimeSlotIndex() == timeSlotDTO.getIndex()).toList();
        if (filteredBookings.size() > 1) {
            throw new IllegalStateException("Duplicate Booking for timeslot: " + timeSlotDTO.getIndex());
        }

        return filteredBookings.size() == 1 ? Optional.of(filteredBookings.get(0)) : Optional.empty();
    }
}
