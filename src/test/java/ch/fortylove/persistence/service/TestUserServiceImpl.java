package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.dto.PrivilegeDTO;
import ch.fortylove.persistence.dto.RoleDTO;
import ch.fortylove.persistence.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringTest
class TestUserServiceImpl {

    @Autowired PrivilegeService privilegeService;
    @Autowired RoleService roleService;

    @Autowired UserService testee;

    @Test
    public void testCreate() {
        final PrivilegeDTO privilegeDTO1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilegeDTO2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO roleDTO1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilegeDTO1)));
        final RoleDTO roleDTO2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilegeDTO2)));
        final UserDTO createdUserDTO = testee.create(new UserDTO(0L, "firstName", "lastName", "email", "password", true, Arrays.asList(roleDTO1, roleDTO2), null));

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdUserDTO, testee.findAll().get(0));
        Assertions.assertTrue(createdUserDTO.getRoles().contains(roleDTO1));
        Assertions.assertTrue(createdUserDTO.getRoles().contains(roleDTO2));
    }


    @Test
    public void testFindByEmail_notExists() {
        final PrivilegeDTO privilegeDTO1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilegeDTO2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO roleDTO1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilegeDTO1)));
        final RoleDTO roleDTO2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilegeDTO2)));
        testee.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(roleDTO1, roleDTO2), null));
        testee.create(new UserDTO(0L, "firstName3", "lastName3", "email3", "password3", true, Arrays.asList(roleDTO1, roleDTO2), null));

        final Optional<UserDTO> user = testee.findByEmail("email2");

        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void testFindByEmail_exists() {
        final PrivilegeDTO privilegeDTO1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilegeDTO2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO roleDTO1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilegeDTO1)));
        final RoleDTO roleDTO2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilegeDTO2)));
        testee.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(roleDTO1, roleDTO2), null));
        final UserDTO userDTO2 = testee.create(new UserDTO(0L, "firstName2", "lastName2", "email2", "password2", true, Arrays.asList(roleDTO1, roleDTO2), null));
        testee.create(new UserDTO(0L, "firstName3", "lastName3", "email3", "password3", true, Arrays.asList(roleDTO1, roleDTO2), null));

        final Optional<UserDTO> user = testee.findByEmail("email2");

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(userDTO2, user.get());
    }
}