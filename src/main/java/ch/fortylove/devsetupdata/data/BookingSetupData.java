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

        getCourt(CourtSetupData.COURT_IDS[0]).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER3), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[0]).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[0]).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER2), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[0]).ifPresent(court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), today, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER3), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER2), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER3), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER1), today, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[2]).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER2), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[2]).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER2), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[2]).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER1), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[2]).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER2), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[2]).ifPresent(court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER1), today, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[3]).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[3]).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER3), today, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[4]).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER1), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[4]).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER2), today, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[5]).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), today, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[5]).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER2), today, timeslot)));
    }

    private void createBookingsYesterday(@Nonnull final List<Timeslot> timeslots) {
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        getCourt(CourtSetupData.COURT_IDS[0]).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), yesterday, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[0]).ifPresent(court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER3), yesterday, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[0]).ifPresent(court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER1), yesterday, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER1), yesterday, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER1), yesterday, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER2), yesterday, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), yesterday, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[2]).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER1), yesterday, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[3]).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER3), yesterday, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[3]).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER1), yesterday, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[4]).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER2), yesterday, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[4]).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER3), yesterday, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[5]).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER2), yesterday, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[5]).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER1), yesterday, timeslot)));
    }

    private void createBookingsTomorrow(@Nonnull final List<Timeslot> timeslots) {
        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        getCourt(CourtSetupData.COURT_IDS[0]).ifPresent(court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER3), tomorrow, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[1]).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), tomorrow, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[2]).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER1), tomorrow, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[3]).ifPresent(court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER1), tomorrow, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[3]).ifPresent(court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), tomorrow, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[4]).ifPresent(court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER1), getOpponents(UserSetupData.USER2), tomorrow, timeslot)));
        getCourt(CourtSetupData.COURT_IDS[4]).ifPresent(court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER2), getOpponents(UserSetupData.USER3), tomorrow, timeslot)));

        getCourt(CourtSetupData.COURT_IDS[5]).ifPresent(court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserSetupData.USER3), getOpponents(UserSetupData.USER2), tomorrow, timeslot)));
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
    private List<User> getOpponents(@Nonnull final String... opponents) {
        final ArrayList<User> opponentsList = new ArrayList<>();
        for (final String opponent : opponents) {
            userService.findByEmail(opponent).ifPresent(opponentsList::add);
        }

        return opponentsList;
    }

    private User getOwner(final String owner) {
        return userService.findByEmail(owner).get();
    }

    @Nonnull
    private Optional<Court> getCourt(final long id) {
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
            final Booking booking = new Booking(court, player, partners, timeslot, date);
            bookingService.create(booking);
            court.addBooking(booking);
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