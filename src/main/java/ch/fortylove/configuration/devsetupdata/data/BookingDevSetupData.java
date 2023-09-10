package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.service.BookingService;
import ch.fortylove.service.BookingSettingsService;
import ch.fortylove.service.CourtService;
import ch.fortylove.service.UserService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.function.Consumer;

@DevSetupData
public class BookingDevSetupData {

    @Nonnull private final CourtService courtService;
    @Nonnull private final BookingService bookingService;
    @Nonnull private final UserService userService;
    @Nonnull private final BookingSettingsService bookingSettingsService;

    @Autowired
    public BookingDevSetupData(@Nonnull final CourtService courtService,
                               @Nonnull final BookingService bookingService,
                               @Nonnull final UserService userService,
                               @Nonnull final BookingSettingsService bookingSettingsService) {
        this.courtService = courtService;
        this.bookingService = bookingService;
        this.userService = userService;
        this.bookingSettingsService = bookingSettingsService;
    }

    public void createBookings() {
        final SortedSet<Timeslot> timeslots = bookingSettingsService.getBookingSettings().getTimeslots();
        final List<Court> courts = courtService.findAll();
        createBookingsToday(courts, timeslots);
        createBookingsYesterday(courts, timeslots);
        createBookingsTomorrow(courts, timeslots);
    }

    private void createBookingsToday(@Nonnull final List<Court> courts,
                                     @Nonnull final SortedSet<Timeslot> timeslots) {
        final LocalDate today = LocalDate.now();
        final SortedMap<Integer, List<Consumer<Court>>> bookings = new TreeMap<>();

        bookings.put(0, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER3), today, timeslot)),
                court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), today, timeslot)),
                court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER2), today, timeslot)),
                court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), today, timeslot))
        ));

        bookings.put(1, List.of(
                court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), today, timeslot)),
                court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER3), today, timeslot)),
                court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), today, timeslot)),
                court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER2), today, timeslot)),
                court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER3), today, timeslot)),
                court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER1), today, timeslot))
        ));

        bookings.put(2, List.of(
                court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER2), today, timeslot)),
                court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER2), today, timeslot)),
                court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER1), today, timeslot)),
                court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER2), today, timeslot)),
                court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER1), today, timeslot))
        ));

        bookings.put(3, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), today, timeslot)),
                court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER3), today, timeslot))
        ));

        bookings.put(4, List.of(
                court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER1), today, timeslot)),
                court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER2), today, timeslot))
        ));

        bookings.put(5, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), today, timeslot)),
                court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER2), today, timeslot))
        ));

        createBookings(courts, bookings);
    }

    private void createBookingsYesterday(@Nonnull final List<Court> courts,
                                         @Nonnull final SortedSet<Timeslot> timeslots) {
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        final SortedMap<Integer, List<Consumer<Court>>> bookings = new TreeMap<>();

        bookings.put(0, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), yesterday, timeslot)),
                court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER3), yesterday, timeslot)),
                court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER1), yesterday, timeslot))
        ));

        bookings.put(1, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER1), yesterday, timeslot)),
                court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER1), yesterday, timeslot)),
                court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER2), yesterday, timeslot)),
                court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), yesterday, timeslot))
        ));

        bookings.put(2, List.of(
                court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER1), yesterday, timeslot))
        ));

        bookings.put(3, List.of(
                court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER3), yesterday, timeslot)),
                court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER1), yesterday, timeslot))
        ));

        bookings.put(4, List.of(
                court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER2), yesterday, timeslot)),
                court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER3), yesterday, timeslot))
        ));

        bookings.put(5, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER2), yesterday, timeslot)),
                court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER1), yesterday, timeslot))
        ));

        createBookings(courts, bookings);
    }

    private void createBookingsTomorrow(@Nonnull final List<Court> courts,
                                        @Nonnull final SortedSet<Timeslot> timeslots) {
        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        final SortedMap<Integer, List<Consumer<Court>>> bookings = new TreeMap<>();

        bookings.put(0, List.of(
                court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER3), tomorrow, timeslot)),
                court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), tomorrow, timeslot))
        ));

        bookings.put(1, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER1), tomorrow, timeslot)),
                court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER1), tomorrow, timeslot)),
                court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), tomorrow, timeslot))
        ));

        bookings.put(2, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER2), tomorrow, timeslot))
        ));

        bookings.put(3, List.of(
                court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER2), getOpponents(UserDevSetupData.USER3), tomorrow, timeslot))
        ));

        bookings.put(4, List.of(
                court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER3), getOpponents(UserDevSetupData.USER2), tomorrow, timeslot))
        ));

        bookings.put(5, List.of(
                court -> getTimeslot(timeslots, 7).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner(UserDevSetupData.USER1), getOpponents(UserDevSetupData.USER2), tomorrow, timeslot))
        ));

        createBookings(courts, bookings);
    }

    private void createBookings(@Nonnull final List<Court> courts,
                                @Nonnull final SortedMap<Integer, List<Consumer<Court>>> bookings) {
        for (int i = 0; i < courts.size(); i++) {
            for (final Consumer<Court> courtConsumer : bookings.get(i)) {
                courtConsumer.accept(courts.get(i));
            }
        }
    }

    @Nonnull
    private Optional<Timeslot> getTimeslot(@Nonnull final SortedSet<Timeslot> timeslots,
                                           final int index) {
        for (final Timeslot timeslot : timeslots) {
            if (timeslot.getIndex() == index) {
                return Optional.of(timeslot);
            }
        }
        return Optional.empty();
    }

    @Nonnull
    private Set<User> getOpponents(@Nonnull final String... opponents) {
        final Set<User> opponentsList = new HashSet<>();
        for (final String opponent : opponents) {
            userService.findByEmail(opponent).ifPresent(opponentsList::add);
        }

        return opponentsList;
    }

    @Nonnull
    private User getOwner(@Nonnull final String owner) {
        return userService.findByEmail(owner).get();
    }

    @Transactional
    private void createBookingIfNotFound(@Nonnull final Court court,
                                 @Nonnull final User player,
                                 @Nonnull final Set<User> partners,
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