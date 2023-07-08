package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringTest
class TestUserServiceImpl {

    @Autowired private PrivilegeService privilegeService;
    @Autowired private RoleService roleService;

    @Autowired private UserService testee;

    @Test
    public void testCreate() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2", null));
        final Role role1 = roleService.create(new Role("roleName1", null, List.of(privilege1)));
        final Role role2 = roleService.create(new Role("roleName2", null, List.of(privilege2)));
        final User createdUser = testee.create(new User("firstName", "lastName", "email@fortylove.ch", "password", true, Arrays.asList(role1, role2), null, null, null));

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdUser, testee.findAll().get(0));
        Assertions.assertTrue(createdUser.getRoles().contains(role1));
        Assertions.assertTrue(createdUser.getRoles().contains(role2));
    }


    @Test
    public void testFindByEmail_notExists() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2", null));
        final Role role1 = roleService.create(new Role("roleName1", null, List.of(privilege1)));
        final Role role2 = roleService.create(new Role("roleName2", null, List.of(privilege2)));
        testee.create(new User("firstName1", "lastName1", "email1@fortylove.ch", "password1", true, Arrays.asList(role1, role2), null, null, null));
        testee.create(new User("firstName3", "lastName3", "email3@fortylove.ch", "password3", true, Arrays.asList(role1, role2), null, null, null));

        final Optional<User> user = testee.findByEmail("email2");

        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void testFindByEmail_exists() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2", null));
        final Role role1 = roleService.create(new Role("roleName1", null, List.of(privilege1)));
        final Role role2 = roleService.create(new Role("roleName2", null, List.of(privilege2)));
        testee.create(new User("firstName1", "lastName1", "email1@fortylove.ch", "password1", true, Arrays.asList(role1, role2), null, null, null));
        final User user2 = testee.create(new User("firstName2", "lastName2", "email2@fortylove.ch", "password2", true, Arrays.asList(role1, role2), null, null, null));
        testee.create(new User("firstName3", "lastName3", "email3@fortylove.ch", "password3", true, Arrays.asList(role1, role2), null, null, null));

        final Optional<User> user = testee.findByEmail("email2@fortylove.ch");

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(user2, user.get());
    }
}