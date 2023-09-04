package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Privilege;
import ch.fortylove.persistence.entity.Role;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

@SpringTest
class TestRoleService extends ServiceTest {

    @Autowired private RoleService testee;

    @Nonnull private Privilege privilege;

    @Autowired
    public TestRoleService(@Nonnull final RoleService testee) {
        this.testee = testee;
    }

    @BeforeEach
    void setUp() {
        privilege = getTestDataFactory().getPrivilegeDataFactory().getDefault();
    }

    @Test
    public void testCreate() {
        final String roleName = "roleName";

        final Role createdRole = testee.create(new Role(roleName, Set.of(privilege))).getData().get();

        final Optional<Role> foundRole = testee.findByName(roleName);
        Assertions.assertTrue(foundRole.isPresent());
        Assertions.assertEquals(createdRole, foundRole.get());
    }

    @Test
    public void testFindByName_notExists() {
        testee.create(new Role("roleName1", Set.of(privilege)));
        testee.create(new Role("roleName3", Set.of(privilege)));

        final Optional<Role> foundRole = testee.findByName("roleName2");

        Assertions.assertTrue(foundRole.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String roleName2 = "roleName2";
        testee.create(new Role("roleName1", Set.of(privilege)));
        final Role createdRole = testee.create(new Role(roleName2, Set.of(privilege))).getData().get();
        testee.create(new Role("roleName3", Set.of(privilege)));

        final Optional<Role> foundRole = testee.findByName(roleName2);

        Assertions.assertTrue(foundRole.isPresent());
        Assertions.assertEquals(createdRole, foundRole.get());
    }
}