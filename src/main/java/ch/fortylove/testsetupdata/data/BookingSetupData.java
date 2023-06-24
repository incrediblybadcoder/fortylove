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
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "daniel"), today, 7));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), today, 8));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), today, 9));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), today, 10));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), today, 11));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), today, 9));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), today, 10));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), today, 11));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), today, 12));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), today, 13));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), today, 15));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), today, 10));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), today, 12));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), today, 13));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), today, 14));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), today, 15));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), today, 8));
        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), today, 10));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), today, 9));
        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), today, 13));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), today, 8));
        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), today, 14));
    }

    private void createBookingsYesterday() {
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), yesterday, 8));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), yesterday, 11));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), yesterday, 15));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), yesterday, 8));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), yesterday, 9));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), yesterday, 13));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), yesterday, 14));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), yesterday, 12));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), yesterday, 9));
        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), yesterday, 14));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), yesterday, 10));
        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), yesterday, 13));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), yesterday, 8));
        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), yesterday, 12));
    }

    private void createBookingsTomorrow() {
        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "marco"), tomorrow, 9));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), tomorrow, 12));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getUsers("marco", "jonas"), tomorrow, 8));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), tomorrow, 12));
        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getUsers("daniel", "marco"), tomorrow, 13));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), tomorrow, 8));
        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), tomorrow, 14));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getUsers("jonas", "daniel"), tomorrow, 10));
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
                                 @Nonnull final LocalDate date,
                                 final int timeSlotIndex) {
        final List<Booking> bookings = bookingService.findAllByCourtId(court.getId());

        if (isNewBooking(bookings, date, timeSlotIndex)) {
            final Booking booking = new Booking();
            booking.setDate(date);
            booking.setTimeSlotIndex(timeSlotIndex);
            booking.setCourt(court);
            booking.setUsers(users);
            bookingService.create(booking);
        }
    }

    private boolean isNewBooking(@Nonnull final List<Booking> bookings,
                                 @Nonnull final LocalDate date,
                                 final int timeSlotIndex) {
        for (final Booking booking : bookings) {
            if (booking.getDate().equals(date) && booking.getTimeSlotIndex() == timeSlotIndex) {
                return false;
            }
        }
        return true;
    }
}