package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Privilege;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@SpringTest
class TestPrivilegeServiceImpl {

    @Autowired
    PrivilegeService testee;

    @Test
    public void testFindAll_emptyRepository() {
        final List<Privilege> privileges = testee.findAll();

        Assertions.assertTrue(privileges.isEmpty());
    }

    @Test
    public void testCreate() {
        final String name = "name";

        final Privilege privilege = createPrivilege(name);

        Assertions.assertEquals(name, privilege.getName());
    }

    @Test
    public void testFindAll() {
        final Privilege privilege1 = createPrivilege("name1");
        final Privilege privilege2 = createPrivilege("name2");
        final Privilege privilege3 = createPrivilege("name3");

        final List<Privilege> privileges = testee.findAll();

        Assertions.assertEquals(3, privileges.size());
        Assertions.assertAll(
                () -> Assertions.assertEquals(privilege1, privileges.get(0)),
                () -> Assertions.assertEquals(privilege2, privileges.get(1)),
                () -> Assertions.assertEquals(privilege3, privileges.get(2))
        );
    }

    @Test
    public void testFindByName_notExists() {
        createPrivilege("name1");
        createPrivilege("name3");

        final Optional<Privilege> privilege = testee.findByName("name2");

        Assertions.assertTrue(privilege.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String name2 = "name2";
        createPrivilege("name1");
        final Privilege privilege2 = createPrivilege(name2);
        createPrivilege("name3");

        final Optional<Privilege> privilege = testee.findByName(name2);

        Assertions.assertTrue(privilege.isPresent());
        Assertions.assertEquals(privilege2, privilege.get());
    }

    @Test
    public void testDeleteById() {
        createPrivilege("name1");
        final Privilege privilege2 = createPrivilege("name2");
        createPrivilege("name3");

        testee.deleteById(privilege2.getId());

        final List<Privilege> privileges = testee.findAll();
        Assertions.assertEquals(2, privileges.size());
        Assertions.assertFalse(privileges.contains(privilege2));
    }

    @Test
    public void testUpdate() {
        final String name2 = "name2";
        final long privilegeId = createPrivilege("name1").getId();

        testee.update(privilegeId, new Privilege(name2));

        final List<Privilege> privileges = testee.findAll();
        Assertions.assertEquals(1, privileges.size());
        Assertions.assertEquals(privilegeId, privileges.get(0).getId());
        Assertions.assertEquals(name2, privileges.get(0).getName());
    }

    @Nonnull
    private Privilege createPrivilege(@Nonnull final String name) {
        final Privilege privilege = new Privilege(name);
        return testee.create(privilege);
    }
}