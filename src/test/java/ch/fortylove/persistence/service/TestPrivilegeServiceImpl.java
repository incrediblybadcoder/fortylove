package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Privilege;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringTest
class TestPrivilegeServiceImpl {

    @Autowired private PrivilegeService testee;

    @Test
    public void testCreate() {
        final Privilege createdPrivilege = testee.create(new Privilege("name"));

        Assertions.assertTrue(testee.findById(createdPrivilege.getId()).isPresent());
        Assertions.assertEquals(createdPrivilege, testee.findById(createdPrivilege.getId()).get());
    }

    @Test
    public void testFindByName_notExists() {
        testee.create(new Privilege("name1"));
        testee.create(new Privilege("name3"));

        final Optional<Privilege> privilege = testee.findByName("name2");

        Assertions.assertTrue(privilege.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String name2 = "name2";
        testee.create(new Privilege("name1"));
        final Privilege privilege2 = testee.create(new Privilege(name2));
        testee.create(new Privilege("name3"));

        final Optional<Privilege> privilege = testee.findByName(name2);

        Assertions.assertTrue(privilege.isPresent());
        Assertions.assertEquals(privilege2, privilege.get());
    }

    @Test
    public void testDelete() {
        testee.create(new Privilege("name1"));
        final Privilege privilege2 = testee.create(new Privilege("name2"));
        testee.create(new Privilege("name3"));

        testee.delete(privilege2.getId());

        final Optional<Privilege> findDeleted = testee.findById(privilege2.getId());
        Assertions.assertTrue(findDeleted.isEmpty());
    }
}