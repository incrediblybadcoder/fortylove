package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.repository.BookingRepository;
import ch.fortylove.persistence.repository.CourtRepository;
import ch.fortylove.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;

@Component
@Profile({"h2", "develop", "local"})
public class BookingSetupData {

    @Nonnull private final CourtRepository courtRepository;
    @Nonnull private final BookingRepository bookingRepository;
    @Nonnull private final UserRepository userRepository;

    @Autowired
    public BookingSetupData(@Nonnull final CourtRepository courtRepository,
                            @Nonnull final BookingRepository bookingRepository,
                            @Nonnull final UserRepository userRepository) {
        this.courtRepository = courtRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public void createBookings() {
        createBookingIfNotFound(getCourt(1L), getUsers("marco", "daniel"), 1);
        createBookingIfNotFound(getCourt(1L), getUsers("daniel", "marco"), 4);
        createBookingIfNotFound(getCourt(1L), getUsers("jonas", "marco"), 5);
        createBookingIfNotFound(getCourt(1L), getUsers("jonas", "marco"), 9);
        createBookingIfNotFound(getCourt(1L), getUsers("jonas", "marco"), 12);

        createBookingIfNotFound(getCourt(2L), getUsers("daniel", "marco"), 1);
        createBookingIfNotFound(getCourt(2L), getUsers("daniel", "marco"), 2);
        createBookingIfNotFound(getCourt(2L), getUsers("daniel", "marco"), 3);
        createBookingIfNotFound(getCourt(2L), getUsers("daniel", "marco"), 5);
        createBookingIfNotFound(getCourt(2L), getUsers("daniel", "marco"), 13);
        createBookingIfNotFound(getCourt(2L), getUsers("daniel", "marco"), 15);

        createBookingIfNotFound(getCourt(3L), getUsers("jonas", "daniel"), 6);
        createBookingIfNotFound(getCourt(3L), getUsers("jonas", "daniel"), 8);

        createBookingIfNotFound(getCourt(4L), getUsers("marco", "jonas"), 11);
        createBookingIfNotFound(getCourt(4L), getUsers("marco", "jonas"), 13);
        createBookingIfNotFound(getCourt(4L), getUsers("marco", "jonas"), 14);

        createBookingIfNotFound(getCourt(5L), getUsers("daniel", "marco"), 5);
        createBookingIfNotFound(getCourt(5L), getUsers("daniel", "marco"), 6);

        createBookingIfNotFound(getCourt(6L), getUsers("jonas", "daniel"), 3);
        createBookingIfNotFound(getCourt(6L), getUsers("jonas", "daniel"), 7);
        createBookingIfNotFound(getCourt(6L), getUsers("jonas", "daniel"), 14);
        createBookingIfNotFound(getCourt(6L), getUsers("jonas", "daniel"), 15);
    }

    @Nonnull
    @Transactional
    private Collection<User> getUsers(@Nonnull final String user1,
                                      @Nonnull final String user2) {
        return Arrays.asList(
                userRepository.findByEmail(user1),
                userRepository.findByEmail(user2)
        );
    }

    @Nullable
    @Transactional
    private Court getCourt(final long id) {
        return courtRepository.findById(id);
    }

    @Transactional
    void createBookingIfNotFound(@Nullable final Court court,
                                 @Nonnull final Collection<User> users,
                                 final int timeslot) {
        if (court != null) {
            final Collection<Booking> bookings = bookingRepository.findAllByCourtId(court.getId());
            if (isNewBooking(bookings, timeslot)) {
                final Booking booking = new Booking();
                booking.setTimeslot(timeslot);
                booking.setCourt(court);
                booking.setUsers(users);
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