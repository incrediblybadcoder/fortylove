package ch.fortylove.testsetupdata.data;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.BookingService;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.UserService;
import ch.fortylove.testsetupdata.TestSetupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestSetupData
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
        createBookingsToday();
        createBookingsYesterday();
        createBookingsTomorrow();
    }

    private void createBookingsToday() {
        final LocalDate today = LocalDate.now();
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "daniel"), LocalDateTime.of(today, LocalTime.of(8, 0))));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(today, LocalTime.of(9, 0))));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), LocalDateTime.of(today, LocalTime.of(10, 0))));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), LocalDateTime.of(today, LocalTime.of(13, 0))));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), LocalDateTime.of(today, LocalTime.of(14, 0))));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(today, LocalTime.of(9, 0))));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(today, LocalTime.of(10, 0))));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(today, LocalTime.of(11, 0))));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(today, LocalTime.of(12, 0))));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(today, LocalTime.of(13, 0))));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(today, LocalTime.of(15, 0))));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(today, LocalTime.of(8, 0))));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(today, LocalTime.of(10, 0))));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), LocalDateTime.of(today, LocalTime.of(13, 0))));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), LocalDateTime.of(today, LocalTime.of(14, 0))));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), LocalDateTime.of(today, LocalTime.of(15, 0))));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(today, LocalTime.of(10, 0))));
        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(today, LocalTime.of(12, 0))));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(today, LocalTime.of(9, 0))));
        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(today, LocalTime.of(13, 0))));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(today, LocalTime.of(14, 0))));
        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(today, LocalTime.of(15, 0))));
    }

    private void createBookingsYesterday() {
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(12, 0))));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), LocalDateTime.of(yesterday, LocalTime.of(15, 0))));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), LocalDateTime.of(yesterday, LocalTime.of(16, 0))));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(yesterday, LocalTime.of(8, 0))));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(yesterday, LocalTime.of(11, 0))));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(yesterday, LocalTime.of(13, 0))));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(yesterday, LocalTime.of(14, 0))));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(9, 0))));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), LocalDateTime.of(yesterday, LocalTime.of(11, 0))));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), LocalDateTime.of(yesterday, LocalTime.of(15, 0))));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(yesterday, LocalTime.of(9, 0))));
        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(yesterday, LocalTime.of(10, 0))));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(8, 0))));
        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(15, 0))));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(14, 0))));
        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(15, 0))));
    }

    private void createBookingsTomorrow() {
        final LocalDate yesterday = LocalDate.now().plusDays(1);
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(10, 0))));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), LocalDateTime.of(yesterday, LocalTime.of(15, 0))));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(yesterday, LocalTime.of(11, 0))));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(yesterday, LocalTime.of(13, 0))));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(8, 0))));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), LocalDateTime.of(yesterday, LocalTime.of(12, 0))));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), LocalDateTime.of(yesterday, LocalTime.of(8, 0))));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(9, 0))));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(13, 0))));
        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), LocalDateTime.of(yesterday, LocalTime.of(14, 0))));
    }

    @Nonnull
    @Transactional
    private List<User> getUsers(@Nonnull final String user1,
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
                                 @Nonnull final List<User> users,
                                 @Nonnull final LocalDateTime dateTime) {
        final List<Booking> bookings = bookingService.findAllByCourtId(court.getId());

        if (isNewBooking(bookings, dateTime)) {
            final Booking booking = new Booking();
            booking.setDateTime(dateTime);
            booking.setCourt(court);
            booking.setUsers(users);
            bookingService.create(booking);
        }
    }

    private boolean isNewBooking(@Nonnull final List<Booking> bookings,
                                 @Nonnull final LocalDateTime dateTime) {
        for (final Booking booking : bookings) {
            if (booking.getDateTime().equals(dateTime)) {
                return false;
            }
        }
        return true;
    }
}