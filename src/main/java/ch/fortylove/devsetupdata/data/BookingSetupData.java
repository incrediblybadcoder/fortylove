package ch.fortylove.devsetupdata.data;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.BookingService;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DevSetupData
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
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), today, 8));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("marco"), today, 9));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("marco"), today, 10));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("marco"), today, 11));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), today, 9));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), today, 10));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), today, 11));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), today, 12));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), today, 13));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), today, 15));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), today, 10));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), today, 12));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getOwner("marco"), getOpponents("jonas"), today, 13));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getOwner("marco"), getOpponents("jonas"), today, 14));
        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getOwner("marco"), getOpponents("jonas"), today, 15));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), today, 8));
        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("marco"), today, 10));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("jonas"), today, 9));
        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), today, 13));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("jonas"), today, 8));
        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), today, 14));
    }

    private void createBookingsYesterday() {
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("marco"), yesterday, 8));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("marco"), yesterday, 11));
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("marco"), yesterday, 15));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), yesterday, 8));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), yesterday, 9));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), yesterday, 13));
        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), yesterday, 14));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getOwner("marco"), getOpponents("jonas"), yesterday, 12));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("jonas"), yesterday, 9));
        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("jonas"), yesterday, 14));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), yesterday, 10));
        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), yesterday, 13));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), yesterday, 8));
        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), yesterday, 12));
    }

    private void createBookingsTomorrow() {
        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        getCourt(1L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("marco"), tomorrow, 9));

        getCourt(2L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), tomorrow, 12));

        getCourt(3L).ifPresent(court -> createBookingIfNotFound(court, getOwner("marco"), getOpponents("jonas"), tomorrow, 8));

        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), tomorrow, 12));
        getCourt(4L).ifPresent(court -> createBookingIfNotFound(court, getOwner("daniel"), getOpponents("marco"), tomorrow, 13));

        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), tomorrow, 8));
        getCourt(5L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), tomorrow, 14));

        getCourt(6L).ifPresent(court -> createBookingIfNotFound(court, getOwner("jonas"), getOpponents("daniel"), tomorrow, 10));
    }

    @Nonnull
    @Transactional
    private List<User> getOpponents(@Nonnull final String... opponents) {
        final ArrayList<User> opponentsList = new ArrayList<>();
        for (final String player : opponents) {
            userService.findByEmail(player).ifPresent(opponentsList::add);
        }

        return opponentsList;
    }

    private User getOwner(final String owner) {
        return userService.findByEmail(owner).get();
    }

    @Nonnull
    @Transactional
    private Optional<Court> getCourt(final long id) {
        return courtService.findById(id);
    }

    @Transactional
    void createBookingIfNotFound(@Nonnull final Court court,
                                 @Nonnull final User player,
                                 @Nonnull final List<User> partners,
                                 @Nonnull final LocalDate date,
                                 final int timeSlotIndex) {
        final List<Booking> bookingDTOs = bookingService.findAllByCourtId(court.getId());

        if (isNewBooking(bookingDTOs, date, timeSlotIndex)) {
            final Booking booking = new Booking(0L, court, player, partners, timeSlotIndex, date);
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