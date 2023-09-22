package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.repository.BookingRepository;
import ch.fortylove.service.BookingService;
import ch.fortylove.service.BookingSettingsService;
import ch.fortylove.service.CourtService;
import ch.fortylove.service.UserService;
import ch.fortylove.util.DateTimeUtil;
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
public class BookingDevSetupData implements ch.fortylove.configuration.devsetupdata.data.DevSetupData {

    @Nonnull private final CourtService courtService;
    @Nonnull private final BookingService bookingService;
    @Nonnull private final BookingRepository bookingRepository;
    @Nonnull private final UserService userService;
    @Nonnull private final BookingSettingsService bookingSettingsService;
    @Nonnull private final DateTimeUtil dateTimeUtil;

    @Autowired
    public BookingDevSetupData(@Nonnull final CourtService courtService,
                               @Nonnull final BookingService bookingService,
                               @Nonnull final BookingRepository bookingRepository,
                               @Nonnull final UserService userService,
                               @Nonnull final BookingSettingsService bookingSettingsService,
                               @Nonnull final DateTimeUtil dateTimeUtil) {
        this.courtService = courtService;
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.bookingSettingsService = bookingSettingsService;
        this.dateTimeUtil = dateTimeUtil;
    }

    @Override
    public void createDevData() {
        final SortedSet<Timeslot> timeslots = bookingSettingsService.getBookingSettings().getTimeslots();
        final List<Court> courts = courtService.findAll();
        createBookingsToday(courts, timeslots);
        createBookingsYesterday(courts, timeslots);
        createBookingsTomorrow(courts, timeslots);
    }

    private void createBookingsToday(@Nonnull final List<Court> courts,
                                     @Nonnull final SortedSet<Timeslot> timeslots) {
        final LocalDate today = dateTimeUtil.today();
        final SortedMap<Integer, List<Consumer<Court>>> bookings = new TreeMap<>();

        bookings.put(0, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("lukas.meier@yahoo.com"), getOpponents("tobias.wolf@hotmail.de"), today, timeslot)),
                court -> getTimeslot(timeslots, 10).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("sara.frey@yahoo.com"), getOpponents("jihoon.lee@gmail.com"), today, timeslot))
        ));

        bookings.put(1, List.of(
                court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("marco.solombrino@gmail.com"), getOpponents("lena.müller@hotmail.com"), today, timeslot))
        ));

        bookings.put(2, List.of(
                court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("maria.bianchi@yahoo.it"), getOpponents("roberto.rossi@gmx.it"), today, timeslot))
        ));

        bookings.put(3, List.of(
                court -> getTimeslot(timeslots, 20).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas.cahenzli@gmail.com"), getOpponents("martin.weiss@hotmail.com"), today, timeslot))
        ));

        bookings.put(4, List.of(
        ));

        bookings.put(5, List.of(
        ));

        createBookings(courts, bookings);
    }

    private void createBookingsYesterday(@Nonnull final List<Court> courts,
                                         @Nonnull final SortedSet<Timeslot> timeslots) {
        final LocalDate yesterday = dateTimeUtil.today().minusDays(1);
        final SortedMap<Integer, List<Consumer<Court>>> bookings = new TreeMap<>();

        bookings.put(0, List.of(
                court -> getTimeslot(timeslots, 11).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("lukas.meier@yahoo.com"), getOpponents("lena.müller@hotmail.com"), yesterday, timeslot)),
                court -> getTimeslot(timeslots, 15).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("david.huber@gmx.ch"), getOpponents("jihoon.lee@gmail.com"), yesterday, timeslot))
        ));

        bookings.put(1, List.of(
                court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("david.huber@gmx.ch"), getOpponents("sara.frey@yahoo.com"), yesterday, timeslot))
        ));

        bookings.put(2, List.of(
        ));

        bookings.put(3, List.of(
                court -> getTimeslot(timeslots, 9).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("sophie.gerber@yahoo.com"), getOpponents("martin.weiss@hotmail.com"), yesterday, timeslot))
        ));

        bookings.put(4, List.of(
                court -> getTimeslot(timeslots, 13).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("benjamin.graf@hotmail.com"), getOpponents("laura.fuchs@gmail.com"), yesterday, timeslot))
        ));

        bookings.put(5, List.of(
                court -> getTimeslot(timeslots, 19).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("roberto.rossi@gmx.it"), getOpponents("jonas.cahenzli@gmail.com"), yesterday, timeslot))
        ));

        createBookings(courts, bookings);
    }

    private void createBookingsTomorrow(@Nonnull final List<Court> courts,
                                        @Nonnull final SortedSet<Timeslot> timeslots) {
        final LocalDate tomorrow = dateTimeUtil.today().plusDays(1);
        final SortedMap<Integer, List<Consumer<Court>>> bookings = new TreeMap<>();

        bookings.put(0, List.of(
                court -> getTimeslot(timeslots, 12).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("julia.schmidt@gmx.de"), getOpponents("martin.weiss@hotmail.com"), tomorrow, timeslot))
        ));

        bookings.put(1, List.of(
                court -> getTimeslot(timeslots, 8).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("david.huber@gmx.ch"), getOpponents("marco.solombrino@gmail.com"), tomorrow, timeslot))
        ));

        bookings.put(2, List.of(
        ));

        bookings.put(3, List.of(
                court -> getTimeslot(timeslots, 14).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("lena.müller@hotmail.com"), getOpponents("tobias.wolf@hotmail.de"), tomorrow, timeslot))
        ));

        bookings.put(4, List.of(
        ));

        bookings.put(5, List.of(
                court -> getTimeslot(timeslots, 19).ifPresent(timeslot -> createBookingIfNotFound(court, getOwner("jonas.cahenzli@gmail.com"), getOpponents("sara.frey@yahoo.com"), tomorrow, timeslot))
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

    @Transactional
    private void createBookingIfNotFound(@Nonnull final Court court,
                                         @Nonnull final User owner,
                                         @Nonnull final Set<User> opponents,
                                         @Nonnull final LocalDate date,
                                         @Nonnull final Timeslot timeslot) {
        final List<Booking> bookings = bookingService.findAllByCourtId(court.getId());

        if (isNewBooking(bookings, date, timeslot)) {
            final Booking booking = new Booking(court, owner, opponents, timeslot, date);
            bookingRepository.save(booking);    // bypass validation for dev setup data
            court.addBooking(booking);
        }
    }
}