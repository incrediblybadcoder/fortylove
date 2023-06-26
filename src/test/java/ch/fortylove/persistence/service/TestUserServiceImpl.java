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
        final PrivilegeDTO privilege1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilege2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO role1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilege1)));
        final RoleDTO role2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilege2)));
        final UserDTO createdUser = testee.create(new UserDTO(0L, "firstName", "lastName", "email", "password", true, Arrays.asList(role1, role2), null));

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdUser, testee.findAll().get(0));
        Assertions.assertTrue(createdUser.getRoles().contains(role1));
        Assertions.assertTrue(createdUser.getRoles().contains(role2));
    }


    @Test
    public void testFindByEmail_notExists() {
        final PrivilegeDTO privilege1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilege2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO role1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilege1)));
        final RoleDTO role2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilege2)));
        testee.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(role1, role2), null));
        testee.create(new UserDTO(0L, "firstName3", "lastName3", "email3", "password3", true, Arrays.asList(role1, role2), null));

        final Optional<UserDTO> user = testee.findByEmail("email2");

        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void testFindByEmail_exists() {
        final PrivilegeDTO privilege1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilege2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        final RoleDTO role1 = roleService.create(new RoleDTO(0L, "roleName1", null, List.of(privilege1)));
        final RoleDTO role2 = roleService.create(new RoleDTO(0L, "roleName2", null, List.of(privilege2)));
        testee.create(new UserDTO(0L, "firstName1", "lastName1", "email1", "password1", true, Arrays.asList(role1, role2), null));
        final UserDTO user2 = testee.create(new UserDTO(0L, "firstName2", "lastName2", "email2", "password2", true, Arrays.asList(role1, role2), null));
        testee.create(new UserDTO(0L, "firstName3", "lastName3", "email3", "password3", true, Arrays.asList(role1, role2), null));

        final Optional<UserDTO> user = testee.findByEmail("email2");

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(user2, user.get());
    }
}