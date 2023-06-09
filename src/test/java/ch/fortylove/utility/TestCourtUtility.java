package ch.fortylove.utility;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TestCourtUtility {

    @Test
    void testPrepareBookings() {
        final int numberOfBookings = 16;
        final int[] timeslots = {1, 12, 5, 9};
        final Court court = createTestCourt(timeslots);

        final List<Booking> preparedBookings = CourtUtility.prepareBookings(numberOfBookings, court);

        Assertions.assertEquals(16, preparedBookings.size());
        for (int i = 0; i < numberOfBookings; i++) {
            Assertions.assertEquals(i, preparedBookings.get(i).getTimeslot());
        }
    }

    @Test
    void testPrepareBookings_exceptionBookingSize() {
        final int numberOfBookings = 16;
        final int[] timeslots = {1, 12, 5, 9, 2, 3, 4, 6, 7, 8, 10, 11, 13, 14, 15, 16, 17};
        final Court court = createTestCourt(timeslots);

        Assertions.assertThrows(IllegalStateException.class, () -> CourtUtility.prepareBookings(numberOfBookings, court));
    }

    private Court createTestCourt(final int[] timeslots) {
        final Court court = new Court();
        final ArrayList<Booking> bookings = new ArrayList<>();

        for (final int timeslot : timeslots) {
            final Booking booking = new Booking();
            booking.setTimeslot(timeslot);
            bookings.add(booking);
        }

        court.setBookings(bookings);
        return court;
    }
}