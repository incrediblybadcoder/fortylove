package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Privilege;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringTest
class TestPrivilegeServiceImpl {

    @Autowired PrivilegeService testee;

    @Test
    public void testCreate() {
        final Privilege createdPrivilege = testee.create(new Privilege(0L, "name", null));

        Assertions.assertTrue(testee.findById(createdPrivilege.getId()).isPresent());
        Assertions.assertEquals(createdPrivilege, testee.findById(createdPrivilege.getId()).get());
    }

    @Test
    public void testFindByName_notExists() {
        testee.create(new Privilege(0L, "name1", null));
        testee.create(new Privilege(0L, "name3", null));

        final Optional<Privilege> privilege = testee.findByName("name2");

        Assertions.assertTrue(privilege.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String name2 = "name2";
        testee.create(new Privilege(0L, "name1", null));
        final Privilege privilege2 = testee.create(new Privilege(0L, name2, null));
        testee.create(new Privilege(0L, "name3", null));

        final Optional<Privilege> privilege = testee.findByName(name2);

        Assertions.assertTrue(privilege.isPresent());
        Assertions.assertEquals(privilege2, privilege.get());
    }

    @Test
    public void testDelete() {
        testee.create(new Privilege(0L, "name1", null));
        final Privilege privilege2 = testee.create(new Privilege(0L, "name2", null));
        testee.create(new Privilege(0L, "name3", null));

        testee.delete(privilege2);

        final Optional<Privilege> findDeleted = testee.findById(privilege2.getId());
        Assertions.assertTrue(findDeleted.isEmpty());
    }
}