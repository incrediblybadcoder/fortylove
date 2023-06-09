package ch.fortylove.utility;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CourtUtility {

    @Nonnull
    public static List<Booking> prepareBookings(final int amountOfBookings,
                                                @Nonnull final Court court) {
        final List<Booking> bookings = court.getBookings();
        if (bookings.size() > amountOfBookings) {
            throw new IllegalStateException("Bookings to be rendered cannot exceed maximal amount of bookings");
        }

        int counter = 0;
        final List<Booking> preparedBookings = new ArrayList<>();

        for (int i = 0; i < amountOfBookings; i++) {
            if (counter < bookings.size() && bookings.get(counter).getTimeslot() == i) {
                preparedBookings.add(bookings.get(counter));
                counter++;
            } else {
                final Booking emptyBooking = new Booking();
                emptyBooking.setCourt(court);
                emptyBooking.setTimeslot(i);
                preparedBookings.add(emptyBooking);
            }
        }

        return preparedBookings;
    }
}
