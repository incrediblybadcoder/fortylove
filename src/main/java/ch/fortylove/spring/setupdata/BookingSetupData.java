package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.BookingService;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
@Profile({"h2", "develop", "local"})
public class BookingSetupData {

    @Nonnull private final CourtService courtService;
    @Nonnull private final BookingService bookingService;
    @Nonnull private final UserService userService;

    @Autowired
    public BookingSetupData(@Nonnull final CourtService courtService,
                            @Nonnull final BookingService bookingService,
                            @Nonnull final UserService userService) {
        this.courtService = courtService;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    public void createBookings() {
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "daniel"), 1));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), 4));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), 5));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), 9));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), 12));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), 1));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), 2));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), 3));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), 5));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), 13));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), 15));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), 6));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), 8));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), 11));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), 13));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), 14));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), 5));
        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), 6));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), 3));
        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), 7));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), 14));
        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), 15));
    }

    @Nonnull
    @Transactional
    private Collection<User> getUsers(@Nonnull final String user1,
                                      @Nonnull final String user2) {
        final ArrayList<User> users = new ArrayList<>();
        userService.findByEmail(user1).ifPresent(users::add);
        userService.findByEmail(user2).ifPresent(users::add);

        return users;
    }

    @Nonnull
    @Transactional
    private Optional<Court> getCourt(final long id) {
        return courtService.findById(id);
    }

    @Transactional
    void createBookingIfNotFound(@Nonnull final Court court,
                                 @Nonnull final Collection<User> users,
                                 final int timeslot) {
        final Collection<Booking> bookings = bookingService.findAllByCourtId(court.getId());

        if (isNewBooking(bookings, timeslot)) {
            final Booking booking = new Booking();
            booking.setTimeslot(timeslot);
            booking.setCourt(court);
            booking.setUsers(users);
            bookingService.create(booking);
        }
    }

    private boolean isNewBooking(@Nonnull final Collection<Booking> bookings,
                                 final int timeslot) {
        for (final Booking booking : bookings) {
            if (booking.getTimeslot() == timeslot) {
                return false;
            }
        }
        return true;
    }
}