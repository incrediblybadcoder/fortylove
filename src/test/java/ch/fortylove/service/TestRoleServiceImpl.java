package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@SpringTest
class TestRoleServiceImpl extends ServiceTest {

    @Autowired private RoleService testee;

    @Nonnull private Privilege privilege;

    @Autowired
    public TestRoleServiceImpl(@Nonnull final RoleService testee) {
        this.testee = testee;
    }

    @BeforeEach
    void setUp() {
        privilege = getTestDataFactory().getPrivilegeDataFactory().getDefault();
    }

    @Test
    public void testCreate() {
        final String roleName = "roleName";

        final Role createdRole = testee.create(new Role(roleName, List.of(privilege)));

        final Optional<Role> foundRole = testee.findByName(roleName);
        Assertions.assertTrue(foundRole.isPresent());
        Assertions.assertEquals(createdRole, foundRole.get());
    }

    @Test
    public void testFindByName_notExists() {
        testee.create(new Role("roleName1", List.of(privilege)));
        testee.create(new Role("roleName3", List.of(privilege)));

        final Optional<Role> foundRole = testee.findByName("roleName2");

        Assertions.assertTrue(foundRole.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String roleName2 = "roleName2";
        testee.create(new Role("roleName1", List.of(privilege)));
        final Role createdRole = testee.create(new Role(roleName2, List.of(privilege)));
        testee.create(new Role("roleName3", List.of(privilege)));

        final Optional<Role> foundRole = testee.findByName(roleName2);

        Assertions.assertTrue(foundRole.isPresent());
        Assertions.assertEquals(createdRole, foundRole.get());
    }
}