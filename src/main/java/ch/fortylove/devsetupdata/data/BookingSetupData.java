package ch.fortylove.devsetupdata.data;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.BookingService;
import ch.fortylove.persistence.service.BookingSettingsService;
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
    @Nonnull private final BookingSettingsService bookingSettingsService;

    @Autowired
    public BookingSetupData(@Nonnull final CourtService courtService,
                            @Nonnull final BookingService bookingService,
                            @Nonnull final UserService userService,
                            @Nonnull final BookingSettingsService bookingSettingsService) {
        this.courtService = courtService;
        this.bookingService = bookingService;
        this.userService = userService;
        this.bookingSettingsService = bookingSettingsService;
    }

    public void createBookings() {
        final List<Timeslot> timeslots = bookingSettingsService.getBookingSettings().getTimeslots();
        createBookingsToday(timeslots);
        createBookingsYesterday(timeslots);
        createBookingsTomorrow(timeslots);
    }

    private void createBookingsToday(@Nonnull final List<Timeslot> timeslots) {
        final LocalDate today = LocalDate.now();
        getCourt(1L).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));
        getCourt(1L).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));
        getCourt(1L).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));
        getCourt(1L).ifPresent(court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));

        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));
        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));
        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));
        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));
        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));
        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));

        getCourt(3L).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), today, timeslot)));
        getCourt(3L).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), today, timeslot)));
        getCourt(3L).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("marco@fortylove.ch"), getOpponents("jonas@fortylove.ch"), today, timeslot)));
        getCourt(3L).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("marco@fortylove.ch"), getOpponents("jonas@fortylove.ch"), today, timeslot)));
        getCourt(3L).ifPresent(court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("marco@fortylove.ch"), getOpponents("jonas@fortylove.ch"), today, timeslot)));

        getCourt(4L).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));
        getCourt(4L).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("marco@fortylove.ch"), today, timeslot)));

        getCourt(5L).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("jonas@fortylove.ch"), today, timeslot)));
        getCourt(5L).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), today, timeslot)));

        getCourt(6L).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("jonas@fortylove.ch"), today, timeslot)));
        getCourt(6L).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), today, timeslot)));
    }

    private void createBookingsYesterday(@Nonnull final List<Timeslot> timeslots) {
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        getCourt(1L).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("marco@fortylove.ch"), yesterday, timeslot)));
        getCourt(1L).ifPresent(court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("marco@fortylove.ch"), yesterday, timeslot)));
        getCourt(1L).ifPresent(court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("marco@fortylove.ch"), yesterday, timeslot)));

        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), yesterday, timeslot)));
        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), yesterday, timeslot)));
        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), yesterday, timeslot)));
        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), yesterday, timeslot)));

        getCourt(3L).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("marco@fortylove.ch"), getOpponents("jonas@fortylove.ch"), yesterday, timeslot)));

        getCourt(4L).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("jonas@fortylove.ch"), yesterday, timeslot)));
        getCourt(4L).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("jonas@fortylove.ch"), yesterday, timeslot)));

        getCourt(5L).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), yesterday, timeslot)));
        getCourt(5L).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), yesterday, timeslot)));

        getCourt(6L).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), yesterday, timeslot)));
        getCourt(6L).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), yesterday, timeslot)));
    }

    private void createBookingsTomorrow(@Nonnull final List<Timeslot> timeslots) {
        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        getCourt(1L).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("marco@fortylove.ch"), tomorrow, timeslot)));

        getCourt(2L).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), tomorrow, timeslot)));

        getCourt(3L).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("marco@fortylove.ch"), getOpponents("jonas@fortylove.ch"), tomorrow, timeslot)));

        getCourt(4L).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), tomorrow, timeslot)));
        getCourt(4L).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("daniel@fortylove.ch"), getOpponents("marco@fortylove.ch"), tomorrow, timeslot)));

        getCourt(5L).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), tomorrow, timeslot)));
        getCourt(5L).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), tomorrow, timeslot)));

        getCourt(6L).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas@fortylove.ch"), getOpponents("daniel@fortylove.ch"), tomorrow, timeslot)));
    }

    @Nonnull
    private Optional<Timeslot> getTimeslot(@Nonnull final List<Timeslot> timeslots,
                                           final int index) {
        for (final Timeslot timeslot : timeslots) {
            if (timeslot.getIndex() == index) {
                return Optional.of(timeslot);
            }
        }
        return Optional.empty();
    }

    @Nonnull
    @Transactional
    List<User> getOpponents(@Nonnull final String... opponents) {
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
    Optional<Court> getCourt(final long id) {
        return courtService.findById(id);
    }

    @Transactional
    void createBookingIfNotFound(@Nonnull final Court court,
                                 @Nonnull final User player,
                                 @Nonnull final List<User> partners,
                                 @Nonnull final LocalDate date,
                                 @Nonnull final Timeslot timeslot) {
        final List<Booking> bookingDTOs = bookingService.findAllByCourtId(court.getId());

        if (isNewBooking(bookingDTOs, date, timeslot)) {
            final Booking booking = new Booking(0L, court, player, partners, timeslot, date);
            bookingService.create(booking);
        }
    }

    private boolean isNewBooking(@Nonnull final List<Booking> bookings,
                                 @Nonnull final LocalDate date,
                                 @Nonnull final Timeslot timeslot) {
        for (final Booking booking : bookings) {
            if (booking.getDate().equals(date) && booking.getTimeslot() == timeslot) {
                return false;
            }
        }
        return true;
    }
}