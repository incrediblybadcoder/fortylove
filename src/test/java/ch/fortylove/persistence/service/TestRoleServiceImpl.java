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
        final String roleName = "roleName";
        final Privilege privilege1 = privilegeService.create(new Privilege(0L, "privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege(0L, "privilegeName2", null));

        final Role createdRole = testee.create(new Role(0L, roleName, null, Arrays.asList(privilege1, privilege2)));

        Assertions.assertTrue(testee.findByName(roleName).isPresent());
        Assertions.assertEquals(createdRole, testee.findByName(roleName).get());
        Assertions.assertTrue(createdRole.getPrivileges().contains(privilege1));
        Assertions.assertTrue(createdRole.getPrivileges().contains(privilege2));
    }

    @Test
    public void testFindByName_notExists() {
        final Privilege privilege1 = privilegeService.create(new Privilege(0L, "privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege(0L, "privilegeName2", null));
        testee.create(new Role(0L, "roleName1", null, Arrays.asList(privilege1, privilege2)));
        testee.create(new Role(0L, "roleName3", null, Arrays.asList(privilege1, privilege2)));

        final Optional<Role> role = testee.findByName("roleName2");

        Assertions.assertTrue(role.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String roleName2 = "roleName2";
        final Privilege privilege1 = privilegeService.create(new Privilege(0L, "privilegeName1", null));
        final Privilege privilege2 = privilegeService.create(new Privilege(0L, "privilegeName2", null));
        testee.create(new Role(0L, "roleName1", null, Arrays.asList(privilege1, privilege2)));
        final Role role2 = testee.create(new Role(0L, roleName2, null, Arrays.asList(privilege1, privilege2)));
        testee.create(new Role(0L, "roleName3", null, Arrays.asList(privilege1, privilege2)));

        final Optional<Role> role = testee.findByName(roleName2);

        Assertions.assertTrue(role.isPresent());
        Assertions.assertEquals(role2, role.get());
    }
}