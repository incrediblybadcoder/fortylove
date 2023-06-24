package ch.fortylove.views.newbooking.grid.util;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.settings.TimeSlot;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class CourtUtil {

    @Nonnull
    public static Optional<BookingDTO> getBookingForTimeSlot(@Nonnull final List<Booking> bookings,
                                                          @Nonnull final TimeSlot timeSlot) {
        final List<Booking> filteredBookings = bookings.stream().filter(booking -> booking.getTimeSlotIndex() == timeSlot.getIndex()).toList();
        if (filteredBookings.size() > 1) {
            throw new IllegalStateException("Duplicate Booking for timeslot: " + timeSlot.getIndex());
        }

        return filteredBookings.size() == 1 ? Optional.of(getValue(filteredBookings)) : Optional.empty();
    }

    private static BookingDTO getValue(final List<Booking> filteredBookings) {
        final Booking oldBooking = filteredBookings.get(0);
        return new BookingDTO(getCourt(oldBooking), oldBooking.getUsers(), oldBooking.getTimeSlotIndex(), oldBooking.getDate());
    }

    @Nonnull
    private static CourtDTO getCourt(final Booking oldBooking) {
        return new CourtDTO(oldBooking.getCourt().getId(), oldBooking.getCourt().getBookings());
    }
}
