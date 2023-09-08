package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.service.util.DatabaseResult;
import ch.fortylove.service.util.ValidationResult;
import ch.fortylove.util.DateTimeUtil;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@SpringTest
class TestBookingService extends ServiceTest {

    @Nonnull private static final LocalDate DATE = LocalDate.of(2023, Month.SEPTEMBER, 4);
    @Nonnull private static final LocalTime TIME = LocalTime.of(0,0);

    @MockBean private DateTimeUtil dateTimeUtil;

    @Autowired private BookingService testee;

    @Nonnull private Court court;
    @Nonnull private User owner;
    @Nonnull private User opponent;
    @Nonnull private BookingSettings bookingSettings;

    @BeforeEach
    void setUp() {
        Mockito.when(dateTimeUtil.getLocalDate()).thenReturn(DATE);
        Mockito.when(dateTimeUtil.getLocalTime()).thenReturn(TIME);
        Mockito.when(dateTimeUtil.getLocalDateTime()).thenReturn(LocalDateTime.of(DATE, TIME));

        court = getTestDataFactory().getCourtDataFactory().getDefault();
        owner = getTestDataFactory().getUserDataFactory().createUser("owner@fortylove.ch");
        opponent = getTestDataFactory().getUserDataFactory().createUser("opponent@fortylove.ch");
        bookingSettings = getTestDataFactory().getBookingSettingsDataFactory().getDefault();
    }

    @Test
    public void testCreate() {
        final Booking booking = new Booking(court, owner, Set.of(opponent), getTimeslotIterator().next(), DATE);

        final Booking createdBooking = testee.create(booking).getData().get();

        final Optional<Booking> foundBooking = testee.findById(createdBooking.getId());
        Assertions.assertFalse(foundBooking.isEmpty());
        Assertions.assertEquals(createdBooking, foundBooking.get());
    }

    @Test
    public void testCreate_duplicateRecordException() {
        final Booking booking = new Booking(court, owner, Set.of(opponent), getTimeslotIterator().next(), DATE);
        testee.create(booking);

        final DatabaseResult<Booking> result = testee.create(booking);
        Assertions.assertFalse(result.isSuccessful());
    }

    @Test
    public void testFindAllByCourtId_exists() {
        owner.setPlayerStatus(getTestDataFactory().getPlayerStatusDataFactory().createPlayerStatus("test_playerStatus", 1, 2));
        final Court court1 = getTestDataFactory().getCourtDataFactory().createCourt();
        final Court court2 = getTestDataFactory().getCourtDataFactory().createCourt();
        final Iterator<Timeslot> timeslotIterator = getTimeslotIterator();
        final Booking booking1 = testee.create(new Booking(court1, owner, Set.of(opponent), timeslotIterator.next(), DATE)).getData().get();
        final Booking booking2 = testee.create(new Booking(court2, owner, Set.of(opponent), timeslotIterator.next(), DATE.plusDays(1))).getData().get();
        final Booking booking3 = testee.create(new Booking(court2, owner, Set.of(opponent), timeslotIterator.next(), DATE.plusDays(2))).getData().get();

        final List<Booking> foundBookings = testee.findAllByCourtId(court2.getId());

        Assertions.assertAll(
                () -> Assertions.assertFalse(foundBookings.contains(booking1)),
                () -> Assertions.assertTrue(foundBookings.contains(booking2)),
                () -> Assertions.assertTrue(foundBookings.contains(booking3))
        );
    }

    @Test
    public void testFindAllByCourtId_notExist() {
        final Court court1 = getTestDataFactory().getCourtDataFactory().createCourt();
        final Court court2 = getTestDataFactory().getCourtDataFactory().createCourt();
        final Court court3 = getTestDataFactory().getCourtDataFactory().createCourt();
        final Iterator<Timeslot> timeslotIterator = getTimeslotIterator();
        testee.create(new Booking(court1, owner, Set.of(opponent), timeslotIterator.next(), DATE));
        testee.create(new Booking(court2, owner, Set.of(opponent), timeslotIterator.next(), DATE.plusDays(1)));
        testee.create(new Booking(court2, owner, Set.of(opponent), timeslotIterator.next(), DATE.plusDays(2)));

        final List<Booking> foundBookings = testee.findAllByCourtId(court3.getId());

        Assertions.assertTrue(foundBookings.isEmpty());
    }

    @Test
    public void testIsBookingModifiableOnDate_allowed() {
        final LocalDate localDate = DATE;
        final Booking booking = new Booking(court, owner, Set.of(opponent), getTimeslotIterator().next(), localDate.plusDays(1));
        final LocalDateTime currentDateTime = LocalDateTime.of(localDate, TIME);

        final ValidationResult validationResult = testee.isBookingModifiableOnDateInternal(booking, currentDateTime);

        Assertions.assertTrue(validationResult.isSuccessful());
    }

    @Test
    public void testIsBookingModifiableOnDate_notAllowed_dateInPast() {
        final LocalDate localDate = DATE;
        final Booking booking = new Booking(court, owner, Set.of(opponent), getTimeslotIterator().next(), localDate.minusDays(1));
        final LocalDateTime currentDateTime = LocalDateTime.of(localDate, TIME);

        final ValidationResult validationResult = testee.isBookingModifiableOnDateInternal(booking, currentDateTime);

        Assertions.assertFalse(validationResult.isSuccessful());
    }

    @Test
    public void testIsBookingModifiableOnDate_notAllowed_timeInPast() {
        final LocalDate localDate = DATE;
        final Timeslot timeslot = getTimeslotIterator().next();
        final Booking booking = new Booking(court, owner, Set.of(opponent), timeslot, localDate);
        final LocalDateTime currentDateTime = LocalDateTime.of(localDate, timeslot.getStartTime().plusSeconds(1));

        final ValidationResult validationResult = testee.isBookingModifiableOnDateInternal(booking, currentDateTime);

        Assertions.assertFalse(validationResult.isSuccessful());
    }

    @Test
    public void testIsBookingCreatableOnDate_allowed() {
        final LocalDate localDate = DATE;
        final LocalDate bookingDate = localDate.plusDays(1);
        final LocalDateTime currentDateTime = LocalDateTime.of(localDate, TIME);

        final ValidationResult validationResult = testee.isBookingCreatableOnDate(UUID.randomUUID(), court, owner, getTimeslotIterator().next(), bookingDate, currentDateTime);

        Assertions.assertTrue(validationResult.isSuccessful());
    }

    @Test
    public void testIsBookingCreatableOnDate_notAllowed_dateInPast() {
        final LocalDate localDate = DATE;
        final LocalDate bookingDate = localDate.minusDays(1);
        final LocalDateTime currentDateTime = LocalDateTime.of(localDate, TIME);

        final ValidationResult validationResult = testee.isBookingCreatableOnDate(UUID.randomUUID(), court, owner, getTimeslotIterator().next(), bookingDate, currentDateTime);

        Assertions.assertFalse(validationResult.isSuccessful());
    }


    @Test
    public void testIsBookingCreatableOnDate_notAllowed_bookingExists() {
        final Booking booking = new Booking(court, owner, Set.of(opponent), getTimeslotIterator().next(), DATE);
        testee.create(booking);

        final LocalDate localDate = DATE;
        final LocalDateTime currentDateTime = LocalDateTime.of(localDate, TIME);
        final ValidationResult validationResult = testee.isBookingCreatableOnDate(UUID.randomUUID(), court, owner, getTimeslotIterator().next(), localDate, currentDateTime);

        Assertions.assertFalse(validationResult.isSuccessful());
    }

    @Nonnull
    private Iterator<Timeslot> getTimeslotIterator() {
        final Set<Timeslot> timeslots = bookingSettings.getTimeslots();
        return timeslots.iterator();
    }
}