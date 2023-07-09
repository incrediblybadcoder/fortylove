package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Privilege;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Optional;

@SpringTest
class TestPrivilegeServiceImpl extends ServiceTest {

    @Nonnull private final PrivilegeService testee;

    @Autowired
    public TestPrivilegeServiceImpl(@Nonnull final PrivilegeService testee) {
        this.testee = testee;
    }

    @Test
    public void testCreate() {
        final Privilege createdPrivilege = testee.create(new Privilege("name"));

        final Optional<Privilege> foundPrivilege = testee.findById(createdPrivilege.getId());
        Assertions.assertTrue(foundPrivilege.isPresent());
        Assertions.assertEquals(createdPrivilege, foundPrivilege.get());
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
        final Privilege createdPrivilege = testee.create(new Privilege(name2));
        testee.create(new Privilege("name3"));

        final Optional<Privilege> foundPrivilege = testee.findByName(name2);

        Assertions.assertTrue(foundPrivilege.isPresent());
        Assertions.assertEquals(createdPrivilege, foundPrivilege.get());
    }

    @Test
    public void testDelete() {
        testee.create(new Privilege("name1"));
        final Privilege createdPrivilege = testee.create(new Privilege("name2"));
        testee.create(new Privilege("name3"));

        testee.delete(createdPrivilege.getId());

        final Optional<Privilege> foundPrivilege = testee.findById(createdPrivilege.getId());
        Assertions.assertTrue(foundPrivilege.isEmpty());
    }
}