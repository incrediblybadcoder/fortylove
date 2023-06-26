package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.PrivilegeDTO;
import ch.fortylove.persistence.dto.RoleDTO;
import ch.fortylove.persistence.dto.UserDTO;
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
        final List<BookingDTO> bookings = testee.findAllByCourtId(1L);

        Assertions.assertTrue(bookings.isEmpty());
    }

    @Test
    public void testCreate() {
        final PrivilegeDTO privilege1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilege2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO role1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilege1)));
        final RoleDTO role2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilege2)));
        final UserDTO user1 = userService.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(role1, role2), null));
        final UserDTO user2 = userService.create(new UserDTO(0L, "firstName2", "lastName2", "email2", "password2", true, Arrays.asList(role1, role2), null));
        final BookingDTO booking = new BookingDTO(0L, null, Arrays.asList(user1, user2), 0, null);

        final BookingDTO createdBooking = testee.create(booking);

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdBooking, testee.findAll().get(0));
    }

    @Test
    public void testFindAllByCourtId_exists() {
        final PrivilegeDTO privilege1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilege2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO role1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilege1)));
        final RoleDTO role2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilege2)));
        final UserDTO user1 = userService.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(role1, role2), null));
        final UserDTO user2 = userService.create(new UserDTO(0L, "firstName2", "lastName2", "email2", "password2", true, Arrays.asList(role1, role2), null));
        final CourtDTO court1 = courtService.create(new CourtDTO(0L, null));
        final CourtDTO court2 = courtService.create(new CourtDTO(0L, null));
        final BookingDTO booking1 = testee.create(new BookingDTO(0L, court1, Arrays.asList(user1, user2), 0, null));
        final BookingDTO booking2 = testee.create(new BookingDTO(0L, court2, Arrays.asList(user1, user2), 1, null));
        final BookingDTO booking3 = testee.create(new BookingDTO(0L, court2, Arrays.asList(user1, user2), 2, null));

        final List<BookingDTO> bookings = testee.findAllByCourtId(court2.getId());

        Assertions.assertAll(
                () -> Assertions.assertFalse(bookings.contains(booking1)),
                () -> Assertions.assertTrue(bookings.contains(booking2)),
                () -> Assertions.assertTrue(bookings.contains(booking3))
        );
    }

    @Test
    public void testFindAllByCourtId_notExist() {
        final PrivilegeDTO privilege1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilege2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO role1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilege1)));
        final RoleDTO role2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilege2)));
        final UserDTO user1 = userService.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(role1, role2), null));
        final UserDTO user2 = userService.create(new UserDTO(0L, "firstName2", "lastName2", "email2", "password2", true, Arrays.asList(role1, role2), null));
        final CourtDTO court1 = courtService.create(new CourtDTO(0L, null));
        final CourtDTO court2 = courtService.create(new CourtDTO(0L, null));
        final CourtDTO court3 = courtService.create(new CourtDTO(0L, null));
        testee.create(new BookingDTO(0L, court1, Arrays.asList(user1, user2), 0, null));
        testee.create(new BookingDTO(0L, court2, Arrays.asList(user1, user2), 1, null));
        testee.create(new BookingDTO(0L, court2, Arrays.asList(user1, user2), 2, null));

        final List<BookingDTO> bookings = testee.findAllByCourtId(court3.getId());

        Assertions.assertTrue(bookings.isEmpty());
    }
}