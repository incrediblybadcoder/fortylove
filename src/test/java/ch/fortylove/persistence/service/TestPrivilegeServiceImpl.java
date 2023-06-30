package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.dto.PrivilegeDTO;
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
        final List<PrivilegeDTO> privilegeDTOs = testee.findAll();

        Assertions.assertTrue(privilegeDTOs.isEmpty());
    }

    @Test
    public void testCreate() {
        final PrivilegeDTO createdPrivilegeDTO = testee.create(new PrivilegeDTO(0L, "name", null));

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdPrivilegeDTO, testee.findAll().get(0));
    }

    @Test
    public void testFindAll() {
        final PrivilegeDTO privilegeDTO1 = testee.create(new PrivilegeDTO(0L, "name1", null));
        final PrivilegeDTO privilegeDTO2 = testee.create(new PrivilegeDTO(0L, "name2", null));
        final PrivilegeDTO privilegeDTO3 = testee.create(new PrivilegeDTO(0L, "name3", null));

        final List<PrivilegeDTO> privilegeDTOs = testee.findAll();

        Assertions.assertEquals(3, privilegeDTOs.size());
        Assertions.assertAll(
                () -> Assertions.assertEquals(privilegeDTO1, privilegeDTOs.get(0)),
                () -> Assertions.assertEquals(privilegeDTO2, privilegeDTOs.get(1)),
                () -> Assertions.assertEquals(privilegeDTO3, privilegeDTOs.get(2))
        );
    }

    @Test
    public void testFindByName_notExists() {
        testee.create(new PrivilegeDTO(0L, "name1", null));
        testee.create(new PrivilegeDTO(0L, "name3", null));

        final Optional<PrivilegeDTO> privilege = testee.findByName("name2");

        Assertions.assertTrue(privilege.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        final String name2 = "name2";
        testee.create(new PrivilegeDTO(0L, "name1", null));
        final PrivilegeDTO privilegeDTO2 = testee.create(new PrivilegeDTO(0L, name2, null));;
        testee.create(new PrivilegeDTO(0L, "name3", null));

        final Optional<PrivilegeDTO> privilege = testee.findByName(name2);

        Assertions.assertTrue(privilege.isPresent());
        Assertions.assertEquals(privilegeDTO2, privilege.get());
    }

    @Test
    public void testDeleteById() {
        testee.create(new PrivilegeDTO(0L, "name1", null));
        final PrivilegeDTO privilegeDTO2 = testee.create(new PrivilegeDTO(0L, "name2", null));;
        testee.create(new PrivilegeDTO(0L, "name3", null));

        testee.deleteById(privilegeDTO2.getId());

        final List<PrivilegeDTO> privilegeDTOs = testee.findAll();
        Assertions.assertEquals(2, privilegeDTOs.size());
        Assertions.assertFalse(privilegeDTOs.contains(privilegeDTO2));
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