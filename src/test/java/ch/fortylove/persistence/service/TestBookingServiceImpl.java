package ch.fortylove.persistence.service;

import ch.fortylove.BaseDataTest;
import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.DuplicateRecordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@SpringTest
class TestBookingServiceImpl extends BaseDataTest {

    @Autowired private PrivilegeService privilegeService;
    @Autowired private RoleService roleService;
    @Autowired private UserService userService;
    @Autowired private CourtService courtService;
    @Autowired private BookingSettingsService bookingSettingsService;

    @Autowired private BookingService testee;

    @Test
    public void testFindAllByCourtId_emptyRepository() {
        final List<Booking> bookings = testee.findAllByCourtId(1L);

        Assertions.assertTrue(bookings.isEmpty());
    }

    @Test
    public void testCreate() {
        final Privilege privilege1 = privilegeService.create(new Privilege(0L, "privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege(0L, "privilegeName2", null));
        final Role role1 = roleService.create(new Role(0L, "roleName1", null, List.of(privilege1)));
        final Role role2 = roleService.create(new Role(0L, "roleName2", null, List.of(privilege2)));
        final User user1 = userService.create(new User(0L, "firstName1", "lastName1", "email1@fortylove.ch", "password1", true, Arrays.asList(role1, role2), null, null, null));
        final User user2 = userService.create(new User(0L, "firstName2", "lastName2", "email2@fortylove.ch", "password2", true, Arrays.asList(role1, role2), null, null, null));
        final Court court = courtService.create(new Court(0L, null));
        final Booking booking = new Booking(0L, court, user1, List.of(user2), null, null);

        final Booking createdBooking = testee.create(booking);

        Assertions.assertFalse(testee.findById(createdBooking.getId()).isEmpty());
        Assertions.assertEquals(createdBooking, testee.findById(createdBooking.getId()).get());
    }

    @Test
    public void testCreate_duplicateRecordException() {
        final Privilege privilege1 = privilegeService.create(new Privilege(0L, "privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege(0L, "privilegeName2", null));
        final Role role1 = roleService.create(new Role(0L, "roleName1", null, List.of(privilege1)));
        final Role role2 = roleService.create(new Role(0L, "roleName2", null, List.of(privilege2)));
        final User user1 = userService.create(new User(0L, "firstName1", "lastName1", "email1@fortylove.ch", "password1", true, Arrays.asList(role1, role2), null, null, null));
        final User user2 = userService.create(new User(0L, "firstName2", "lastName2", "email2@fortylove.ch", "password2", true, Arrays.asList(role1, role2), null, null, null));
        final Court court = courtService.create(new Court(0L, null));
        final Booking booking = new Booking(0L, court, user1, List.of(user2), null, null);
        testee.create(booking);

        Assertions.assertThrows(DuplicateRecordException.class, () -> testee.create(booking));
    }

    @Test
    public void testFindAllByCourtId_exists() {
        final List<Timeslot> timeslots = bookingSettingsService.getBookingSettings().getTimeslots();
        final Privilege privilege1 = privilegeService.create(new Privilege(0L, "privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege(0L, "privilegeName2", null));
        final Role role1 = roleService.create(new Role(0L, "roleName1", null, List.of(privilege1)));
        final Role role2 = roleService.create(new Role(0L, "roleName2", null, List.of(privilege2)));
        final User user1 = userService.create(new User(0L, "firstName1", "lastName1", "email1@fortylove.ch", "password1", true, Arrays.asList(role1, role2), null, null, null));
        final User user2 = userService.create(new User(0L, "firstName2", "lastName2", "email2@fortylove.ch", "password2", true, Arrays.asList(role1, role2), null, null, null));
        final Court court1 = courtService.create(new Court(0L, null));
        final Court court2 = courtService.create(new Court(0L, null));
        final Booking booking1 = testee.create(new Booking(0L, court1, user1, List.of(user2), timeslots.get(0), null));
        final Booking booking2 = testee.create(new Booking(0L, court2, user1, List.of(user2), timeslots.get(1), null));
        final Booking booking3 = testee.create(new Booking(0L, court2, user1, List.of(user2), timeslots.get(2), null));

        final List<Booking> bookings = testee.findAllByCourtId(court2.getId());

        Assertions.assertAll(
                () -> Assertions.assertFalse(bookings.contains(booking1)),
                () -> Assertions.assertTrue(bookings.contains(booking2)),
                () -> Assertions.assertTrue(bookings.contains(booking3))
        );
    }

    @Test
    public void testFindAllByCourtId_notExist() {
        final List<Timeslot> timeslots = bookingSettingsService.getBookingSettings().getTimeslots();
        final Privilege privilege1 = privilegeService.create(new Privilege(0L, "privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege(0L, "privilegeName2", null));
        final Role role1 = roleService.create(new Role(0L, "roleName1", null, List.of(privilege1)));
        final Role role2 = roleService.create(new Role(0L, "roleName2", null, List.of(privilege2)));
        final User user1 = userService.create(new User(0L, "firstName1", "lastName1", "email1@fortylove.ch", "password1", true, Arrays.asList(role1, role2), null, null, null));
        final User user2 = userService.create(new User(0L, "firstName2", "lastName2", "email2@fortylove.ch", "password2", true, Arrays.asList(role1, role2), null, null, null));
        final Court court1 = courtService.create(new Court(0L, null));
        final Court court2 = courtService.create(new Court(0L, null));
        final Court court3 = courtService.create(new Court(0L, null));
        testee.create(new Booking(0L, court1, user1, List.of(user2), timeslots.get(0), null));
        testee.create(new Booking(0L, court2, user1, List.of(user2), timeslots.get(1), null));
        testee.create(new Booking(0L, court2, user1, List.of(user2), timeslots.get(2), null));

        final List<Booking> bookings = testee.findAllByCourtId(court3.getId());

        Assertions.assertTrue(bookings.isEmpty());
    }
}