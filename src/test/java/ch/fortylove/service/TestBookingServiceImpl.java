package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.DuplicateRecordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringTest
class TestBookingServiceImpl extends ServiceTest {

    @Nonnull private final BookingService testee;

    @Nonnull private Court court;
    @Nonnull private User owner;
    @Nonnull private User opponent;
    @Nonnull private BookingSettings bookingSettings;

    @Autowired
    public TestBookingServiceImpl(@Nonnull final BookingService testee) {
        this.testee = testee;
    }

    @BeforeEach
    void setUp() {
        court = getTestDataFactory().getCourtDataFactory().getDefault();
        owner = getTestDataFactory().getUserDataFactory().createUser("owner@fortylove.ch");
        opponent = getTestDataFactory().getUserDataFactory().createUser("opponent@fortylove.ch");
        bookingSettings = getTestDataFactory().getBookingSettingsDataFactory().getDefault();
    }

    @Test
    public void testCreate() {
        final Booking booking = new Booking(court, owner, List.of(opponent), bookingSettings.getTimeslots().get(0), LocalDate.now());

        final Booking createdBooking = testee.create(booking);

        final Optional<Booking> foundBooking = testee.findById(createdBooking.getId());
        Assertions.assertFalse(foundBooking.isEmpty());
        Assertions.assertEquals(createdBooking, foundBooking.get());
    }

    @Test
    public void testCreate_duplicateRecordException() {
        final Booking booking = new Booking(court, owner, List.of(opponent), bookingSettings.getTimeslots().get(0), LocalDate.now());
        testee.create(booking);

        Assertions.assertThrows(DuplicateRecordException.class, () -> testee.create(booking));
    }

    @Test
    public void testFindAllByCourtId_exists() {
        final Court court1 = getTestDataFactory().getCourtDataFactory().createCourt();
        final Court court2 = getTestDataFactory().getCourtDataFactory().createCourt();
        final Booking booking1 = testee.create(new Booking(court1, owner, List.of(opponent), bookingSettings.getTimeslots().get(0), LocalDate.now()));
        final Booking booking2 = testee.create(new Booking(court2, owner, List.of(opponent), bookingSettings.getTimeslots().get(1), LocalDate.now().plusDays(1)));
        final Booking booking3 = testee.create(new Booking(court2, owner, List.of(opponent), bookingSettings.getTimeslots().get(2), LocalDate.now().plusDays(2)));

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
        testee.create(new Booking(court1, owner, List.of(opponent), bookingSettings.getTimeslots().get(0), LocalDate.now()));
        testee.create(new Booking(court2, owner, List.of(opponent), bookingSettings.getTimeslots().get(1), LocalDate.now().plusDays(1)));
        testee.create(new Booking(court2, owner, List.of(opponent), bookingSettings.getTimeslots().get(2), LocalDate.now().plusDays(2)));

        final List<Booking> foundBookings = testee.findAllByCourtId(court3.getId());

        Assertions.assertTrue(foundBookings.isEmpty());
    }

    @Test
    public void testIsBookingModifiable_allowed() {
        final Booking booking = new Booking(court, owner, List.of(opponent), bookingSettings.getTimeslots().get(0), LocalDate.now());

        Assertions.assertTrue(testee.isBookingModifiable(owner, booking));
    }

    @Test
    public void testIsBookingModifiable_notAllowed_notOwner() {
        final Booking booking = new Booking(court, owner, List.of(opponent), bookingSettings.getTimeslots().get(0), LocalDate.now());

        Assertions.assertFalse(testee.isBookingModifiable(opponent, booking));
    }

    @Test
    public void testIsBookingModifiable_notAllowed_dateInPast() {
        final Booking booking = new Booking(court, owner, List.of(opponent), bookingSettings.getTimeslots().get(0), LocalDate.now().minusDays(1));

        Assertions.assertFalse(testee.isBookingModifiable(owner, booking));
    }

    @Test
    public void testIsBookingCreatable_allowed() {
        Assertions.assertTrue(testee.isBookingCreatableOnDate(court, bookingSettings.getTimeslots().get(0), LocalDate.now()));
    }

    @Test
    public void testIsBookingCreatable_notAllowed_dateInPast() {
        Assertions.assertFalse(testee.isBookingCreatableOnDate(court, bookingSettings.getTimeslots().get(0), LocalDate.now().minusDays(1)));
    }

    @Test
    public void testIsBookingCreatable_notAllowed_bookingExists() {
        final Booking booking = new Booking(court, owner, List.of(opponent), bookingSettings.getTimeslots().get(0), LocalDate.now());
        testee.create(booking);

        Assertions.assertFalse(testee.isBookingCreatableOnDate(court, bookingSettings.getTimeslots().get(0), LocalDate.now()));
    }
}