package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@SpringTest
class TestRoleServiceImpl {

    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    RoleService testee;

    @Test
    public void testCreate() {
        final String roleName = "roleName";
        final Privilege privilege1 = createPrivilege("privilegeName1");
        final Privilege privilege2 = createPrivilege("privilegeName2");

        final Role role = createRole(roleName, Arrays.asList(privilege1, privilege2));

        Assertions.assertEquals(roleName, role.getName());
        Assertions.assertTrue(role.getPrivileges().contains(privilege1));
        Assertions.assertTrue(role.getPrivileges().contains(privilege2));
    }

    private Privilege createPrivilege(@Nonnull final String name) {
        return privilegeService.create(new Privilege(name));
    }

    @Test
    public void testFindByName_notExists() {
        final Privilege privilege1 = createPrivilege("privilegeName1");
        final Privilege privilege2 = createPrivilege("privilegeName2");
        createRole("roleName1", Arrays.asList(privilege1, privilege2));
        createRole("roleName3", Arrays.asList(privilege1, privilege2));

        final Optional<Role> role = testee.findByName("roleName2");

        Assertions.assertTrue(role.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String roleName2 = "roleName2";
        final Privilege privilege1 = createPrivilege("privilegeName1");
        final Privilege privilege2 = createPrivilege("privilegeName2");
        createRole("roleName1", Arrays.asList(privilege1, privilege2));
        final Role role2 = createRole(roleName2, Arrays.asList(privilege1, privilege2));
        createRole("roleName3", Arrays.asList(privilege1, privilege2));

        final Optional<Role> role = testee.findByName(roleName2);

        Assertions.assertTrue(role.isPresent());
        Assertions.assertEquals(role2, role.get());
    }

    @Nonnull
    private Role createRole(@Nonnull final String name,
                            @Nonnull final Collection<Privilege> privileges) {
        final Role role = new Role(name, privileges);
        return testee.create(role);
    }
}