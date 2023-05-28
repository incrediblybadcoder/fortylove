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

    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService testee;

    @Test
    public void testCreate() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1"));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2"));
        final Role role1 = roleService.create(new Role("roleName1", List.of(privilege1)));
        final Role role2 = roleService.create(new Role("roleName2", List.of(privilege2)));
        final User user = new User("firstName", "lastName", "password", "email", Arrays.asList(role1, role2));

        final User createdUser = testee.create(user);

        Assertions.assertEquals(createdUser, user);
        Assertions.assertTrue(createdUser.getRoles().contains(role1));
        Assertions.assertTrue(createdUser.getRoles().contains(role2));
    }


    @Test
    public void testFindByEmail_notExists() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1"));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2"));
        final Role role1 = roleService.create(new Role("roleName1", List.of(privilege1)));
        final Role role2 = roleService.create(new Role("roleName2", List.of(privilege2)));
        testee.create(new User("firstName1", "lastName1", "password1", "email1", Arrays.asList(role1, role2)));
        testee.create(new User("firstName3", "lastName3", "password3", "email3", Arrays.asList(role1, role2)));

        final Optional<User> user = testee.findByEmail("email2");

        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void testFindByEmail_exists() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1"));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2"));
        final Role role1 = roleService.create(new Role("roleName1", List.of(privilege1)));
        final Role role2 = roleService.create(new Role("roleName2", List.of(privilege2)));
        testee.create(new User("firstName1", "lastName1", "password1", "email1", Arrays.asList(role1, role2)));
        final User user2 = testee.create(new User("firstName2", "lastName2", "password2", "email2", Arrays.asList(role1, role2)));
        testee.create(new User("firstName3", "lastName3", "password3", "email3", Arrays.asList(role1, role2)));

        final Optional<User> user = testee.findByEmail("email2");

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(user2, user.get());
    }
}