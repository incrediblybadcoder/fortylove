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
        final List<BookingDTO> bookingDTOs = testee.findAllByCourtId(1L);

        Assertions.assertTrue(bookingDTOs.isEmpty());
    }

    @Test
    public void testCreate() {
        final PrivilegeDTO privilegeDTO1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilegeDTO2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO roleDTO1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilegeDTO1)));
        final RoleDTO roleDTO2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilegeDTO2)));
        final UserDTO userDTO1 = userService.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(roleDTO1, roleDTO2), null));
        final UserDTO userDTO2 = userService.create(new UserDTO(0L, "firstName2", "lastName2", "email2", "password2", true, Arrays.asList(roleDTO1, roleDTO2), null));
        final BookingDTO booking = new BookingDTO(0L, null, Arrays.asList(userDTO1, userDTO2), 0, null);

        final BookingDTO createdBooking = testee.create(booking);

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdBooking, testee.findAll().get(0));
    }

    @Test
    public void testFindAllByCourtId_exists() {
        final PrivilegeDTO privilegeDTO1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilegeDTO2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO roleDTO1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilegeDTO1)));
        final RoleDTO roleDTO2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilegeDTO2)));
        final UserDTO userDTO1 = userService.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(roleDTO1, roleDTO2), null));
        final UserDTO userDTO2 = userService.create(new UserDTO(0L, "firstName2", "lastName2", "email2", "password2", true, Arrays.asList(roleDTO1, roleDTO2), null));
        final CourtDTO courtDTO1 = courtService.create(new CourtDTO(0L, null));
        final CourtDTO courtDTO2 = courtService.create(new CourtDTO(0L, null));
        final BookingDTO booking1 = testee.create(new BookingDTO(0L, courtDTO1, Arrays.asList(userDTO1, userDTO2), 0, null));
        final BookingDTO booking2 = testee.create(new BookingDTO(0L, courtDTO2, Arrays.asList(userDTO1, userDTO2), 1, null));
        final BookingDTO booking3 = testee.create(new BookingDTO(0L, courtDTO2, Arrays.asList(userDTO1, userDTO2), 2, null));

        final List<BookingDTO> bookingDTOs = testee.findAllByCourtId(courtDTO2.getId());

        Assertions.assertAll(
                () -> Assertions.assertFalse(bookingDTOs.contains(booking1)),
                () -> Assertions.assertTrue(bookingDTOs.contains(booking2)),
                () -> Assertions.assertTrue(bookingDTOs.contains(booking3))
        );
    }

    @Test
    public void testFindAllByCourtId_notExist() {
        final PrivilegeDTO privilegeDTO1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilegeDTO2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO roleDTO1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilegeDTO1)));
        final RoleDTO roleDTO2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilegeDTO2)));
        final UserDTO userDTO1 = userService.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(roleDTO1, roleDTO2), null));
        final UserDTO userDTO2 = userService.create(new UserDTO(0L, "firstName2", "lastName2", "email2", "password2", true, Arrays.asList(roleDTO1, roleDTO2), null));
        final CourtDTO courtDTO1 = courtService.create(new CourtDTO(0L, null));
        final CourtDTO courtDTO2 = courtService.create(new CourtDTO(0L, null));
        final CourtDTO courtDTO3 = courtService.create(new CourtDTO(0L, null));
        testee.create(new BookingDTO(0L, courtDTO1, Arrays.asList(userDTO1, userDTO2), 0, null));
        testee.create(new BookingDTO(0L, courtDTO2, Arrays.asList(userDTO1, userDTO2), 1, null));
        testee.create(new BookingDTO(0L, courtDTO2, Arrays.asList(userDTO1, userDTO2), 2, null));

        final List<BookingDTO> bookingDTOs = testee.findAllByCourtId(courtDTO3.getId());

        Assertions.assertTrue(bookingDTOs.isEmpty());
    }
}