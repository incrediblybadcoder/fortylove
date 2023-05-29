package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

@SpringTest
class TestRoleServiceImpl {

    @Autowired PrivilegeService privilegeService;

    @Autowired RoleService testee;

    @Test
    public void testCreate() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1"));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2"));
        final Role role = new Role("roleName", Arrays.asList(privilege1, privilege2));

        final Role createdRole = testee.create(role);

        Assertions.assertEquals(createdRole, role);
        Assertions.assertTrue(createdRole.getPrivileges().contains(privilege1));
        Assertions.assertTrue(createdRole.getPrivileges().contains(privilege2));
    }

    @Test
    public void testFindByName_notExists() {
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1"));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2"));
        testee.create(new Role("roleName1", Arrays.asList(privilege1, privilege2)));
        testee.create(new Role("roleName3", Arrays.asList(privilege1, privilege2)));

        final Optional<Role> role = testee.findByName("roleName2");

        Assertions.assertTrue(role.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String roleName2 = "roleName2";
        final Privilege privilege1 = privilegeService.create(new Privilege("privilegeName1"));
        final Privilege privilege2 = privilegeService.create(new Privilege("privilegeName2"));
        testee.create(new Role("roleName1", Arrays.asList(privilege1, privilege2)));
        final Role role2 = testee.create(new Role(roleName2, Arrays.asList(privilege1, privilege2)));
        testee.create(new Role("roleName3", Arrays.asList(privilege1, privilege2)));

        final Optional<Role> role = testee.findByName(roleName2);

        Assertions.assertTrue(role.isPresent());
        Assertions.assertEquals(role2, role.get());
    }
}