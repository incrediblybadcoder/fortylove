package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.repository.BookingRepository;
import ch.fortylove.persistence.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

@Component
@Profile("!production")
public class BookingSetupData {
    @Nonnull private final CourtRepository courtRepository;
    @Nonnull private final BookingRepository bookingRepository;

    @Autowired
    public BookingSetupData(@Nonnull final CourtRepository courtRepository,
                            @Nonnull final BookingRepository bookingRepository) {

        this.courtRepository = courtRepository;
        this.bookingRepository = bookingRepository;
    }

    public void createBookings() {
        createBookingIfNotFound(getCourt(1L), 0);
        createBookingIfNotFound(getCourt(1L), 4);
        createBookingIfNotFound(getCourt(1L), 5);
        createBookingIfNotFound(getCourt(1L), 9);
        createBookingIfNotFound(getCourt(1L), 12);

        createBookingIfNotFound(getCourt(2L), 1);
        createBookingIfNotFound(getCourt(2L), 2);
        createBookingIfNotFound(getCourt(2L), 3);
        createBookingIfNotFound(getCourt(2L), 5);
        createBookingIfNotFound(getCourt(2L), 13);
        createBookingIfNotFound(getCourt(2L), 15);

        createBookingIfNotFound(getCourt(3L), 6);
        createBookingIfNotFound(getCourt(3L), 8);

        createBookingIfNotFound(getCourt(4L), 11);
        createBookingIfNotFound(getCourt(4L), 13);
        createBookingIfNotFound(getCourt(4L), 14);

        createBookingIfNotFound(getCourt(5L), 5);
        createBookingIfNotFound(getCourt(5L), 6);

        createBookingIfNotFound(getCourt(6L), 3);
        createBookingIfNotFound(getCourt(6L), 7);
        createBookingIfNotFound(getCourt(6L), 14);
        createBookingIfNotFound(getCourt(6L), 15);
    }

    @Nullable
    private Court getCourt(final long id) {
        return courtRepository.findById(id);
    }

    @Transactional
    void createBookingIfNotFound(@Nullable final Court court,
                                 final int timeslot) {
        if (court != null) {
            final Collection<Booking> bookings = bookingRepository.findAllByCourtId(court.getId());
            if (isNewBooking(bookings, timeslot)) {
                final Booking booking = new Booking();
                booking.setTimeslot(timeslot);
                booking.setCourt(court);
                bookingRepository.save(booking);
            }
        }
    }

    private boolean isNewBooking(final Collection<Booking> bookings,
                                 final int timeslot) {
        for (final Booking booking : bookings) {
            if (booking.getTimeslot() == timeslot) {
                return false;
            }
        }
        return true;
    }
}