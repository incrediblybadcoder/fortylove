package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.dto.PrivilegeDTO;
import ch.fortylove.persistence.dto.RoleDTO;
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
        final PrivilegeDTO privilege1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilege2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));

        final RoleDTO createdRole = testee.create(new RoleDTO(0L, roleName, null, Arrays.asList(privilege1, privilege2)));

        Assertions.assertTrue(testee.findByName(roleName).isPresent());
        Assertions.assertEquals(createdRole, testee.findByName(roleName).get());
        Assertions.assertTrue(createdRole.getPrivileges().contains(privilege1));
        Assertions.assertTrue(createdRole.getPrivileges().contains(privilege2));
    }

    @Test
    public void testFindByName_notExists() {
        final PrivilegeDTO privilege1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilege2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        testee.create(new RoleDTO(0L, "roleName1", null, Arrays.asList(privilege1, privilege2)));
        testee.create(new RoleDTO(0L, "roleName3", null, Arrays.asList(privilege1, privilege2)));

        final Optional<RoleDTO> role = testee.findByName("roleName2");

        Assertions.assertTrue(role.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String roleName2 = "roleName2";
        final PrivilegeDTO privilege1 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName1", null));
        final PrivilegeDTO privilege2 = privilegeService.create(new PrivilegeDTO(0L, "privilegeName2", null));
        testee.create(new RoleDTO(0L, "roleName1", null, Arrays.asList(privilege1, privilege2)));
        final RoleDTO role2 = testee.create(new RoleDTO(0L, roleName2, null, Arrays.asList(privilege1, privilege2)));
        testee.create(new RoleDTO(0L, "roleName3", null, Arrays.asList(privilege1, privilege2)));

        final Optional<RoleDTO> role = testee.findByName(roleName2);

        Assertions.assertTrue(role.isPresent());
        Assertions.assertEquals(role2, role.get());
    }
}