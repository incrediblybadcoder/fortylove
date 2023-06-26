package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.dto.Privilege;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@SpringTest
class TestPrivilegeServiceImpl {

    @Autowired PrivilegeService testee;

    @Test
    public void testFindAll_emptyRepository() {
        final List<Privilege> privileges = testee.findAll();

        Assertions.assertTrue(privileges.isEmpty());
    }

    @Test
    public void testCreate() {
        final Privilege createdPrivilege = testee.create(new Privilege(0L, "name", null));

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdPrivilege, testee.findAll().get(0));
    }

    @Test
    public void testFindAll() {
        final Privilege privilege1 = testee.create(new Privilege(0L, "name1", null));
        final Privilege privilege2 = testee.create(new Privilege(0L, "name2", null));
        final Privilege privilege3 = testee.create(new Privilege(0L, "name3", null));

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
        testee.create(new Privilege(0L, "name1", null));
        testee.create(new Privilege(0L, "name3", null));

        final Optional<Privilege> privilege = testee.findByName("name2");

        Assertions.assertTrue(privilege.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String name2 = "name2";
        testee.create(new Privilege(0L, "name1", null));
        final Privilege privilege2 = testee.create(new Privilege(0L, name2, null));;
        testee.create(new Privilege(0L, "name3", null));

        final Optional<Privilege> privilege = testee.findByName(name2);

        Assertions.assertTrue(privilege.isPresent());
        Assertions.assertEquals(privilege2, privilege.get());
    }

    @Test
    public void testDeleteById() {
        testee.create(new Privilege(0L, "name1", null));
        final Privilege privilege2 = testee.create(new Privilege(0L, "name2", null));;
        testee.create(new Privilege(0L, "name3", null));

        testee.deleteById(privilege2.getId());

        final List<Privilege> privileges = testee.findAll();
        Assertions.assertEquals(2, privileges.size());
        Assertions.assertFalse(privileges.contains(privilege2));
    }

//    @Test
//    public void testUpdate() {
//        final String name2 = "name2";
//        final long privilegeId = testee.create(new PrivilegeDTO(0L, "name1", null)).getId();
//
//        testee.update(privilegeId, new PrivilegeDTO(0L, name2, null));
//
//        final List<PrivilegeDTO> privileges = testee.findAll();
//        Assertions.assertEquals(1, privileges.size());
//        Assertions.assertEquals(privilegeId, privileges.get(0).getId());
//        Assertions.assertEquals(name2, privileges.get(0).getName());
//    }
}