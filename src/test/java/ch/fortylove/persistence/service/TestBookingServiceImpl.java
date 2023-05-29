package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@SpringTest
class TestBookingServiceImpl {

    @Autowired PrivilegeService privilegeService;
    @Autowired RoleService roleService;
    @Autowired UserService userService;
    @Autowired CourtService courtService;

    @Autowired BookingService testee;

    @Test
    public void testFindAllByCourtId_emptyRepository() {
        final List<Booking> bookings = testee.findAllByCourtId(1L);

        Assertions.assertTrue(bookings.isEmpty());
    }

    @Test
    public void testCreate() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1"));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2"));
        final Role role1 = roleService.create(new Role("roleName1", List.of(privilege1)));
        final Role role2 = roleService.create(new Role("roleName2", List.of(privilege2)));
        final User user1 = userService.create(new User("firstName1", "lastName1", "password1", "email1", Arrays.asList(role1, role2)));
        final User user2 = userService.create(new User("firstName3", "lastName3", "password3", "email3", Arrays.asList(role1, role2)));
        final Booking booking = new Booking(Arrays.asList(user1, user2));

        final Booking createdBooking = testee.create(booking);

        Assertions.assertEquals(createdBooking, booking);
    }

    @Test
    public void testFindAllByCourtId_exists() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1"));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2"));
        final Role role1 = roleService.create(new Role("roleName1", List.of(privilege1)));
        final Role role2 = roleService.create(new Role("roleName2", List.of(privilege2)));
        final User user1 = userService.create(new User("firstName1", "lastName1", "password1", "email1", Arrays.asList(role1, role2)));
        final User user2 = userService.create(new User("firstName3", "lastName3", "password3", "email3", Arrays.asList(role1, role2)));
        final Court court1 = courtService.create(new Court());
        final Court court2 = courtService.create(new Court());
        final Booking booking1 = new Booking(Arrays.asList(user1, user2));
        final Booking booking2 = new Booking(Arrays.asList(user1, user2));
        final Booking booking3 = new Booking(Arrays.asList(user1, user2));
        booking1.setCourt(court1);
        booking2.setCourt(court2);
        booking3.setCourt(court2);
        testee.create(booking1);
        testee.create(booking2);
        testee.create(booking3);

        final List<Booking> bookings = testee.findAllByCourtId(court2.getId());

        Assertions.assertAll(
                () -> Assertions.assertFalse(bookings.contains(booking1)),
                () -> Assertions.assertTrue(bookings.contains(booking2)),
                () -> Assertions.assertTrue(bookings.contains(booking3))
        );
    }

    @Test
    public void testFindAllByCourtId_notExist() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1"));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2"));
        final Role role1 = roleService.create(new Role("roleName1", List.of(privilege1)));
        final Role role2 = roleService.create(new Role("roleName2", List.of(privilege2)));
        final User user1 = userService.create(new User("firstName1", "lastName1", "password1", "email1", Arrays.asList(role1, role2)));
        final User user2 = userService.create(new User("firstName3", "lastName3", "password3", "email3", Arrays.asList(role1, role2)));
        final Court court1 = courtService.create(new Court());
        final Court court2 = courtService.create(new Court());
        final Court court3 = courtService.create(new Court());
        final Booking booking1 = new Booking(Arrays.asList(user1, user2));
        final Booking booking2 = new Booking(Arrays.asList(user1, user2));
        final Booking booking3 = new Booking(Arrays.asList(user1, user2));
        booking1.setCourt(court1);
        booking2.setCourt(court2);
        booking3.setCourt(court2);
        testee.create(booking1);
        testee.create(booking2);
        testee.create(booking3);

        final List<Booking> bookings = testee.findAllByCourtId(court3.getId());

        Assertions.assertTrue(bookings.isEmpty());
    }
}