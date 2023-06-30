package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.dto.CourtDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@SpringTest
class TestCourtServiceImpl {

    @Autowired CourtService testee;

    @Test
    public void testCreate() {
        final CourtDTO createdCourtDTO = testee.create(new CourtDTO(0L, null));

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdCourtDTO, testee.findAll().get(0));
    }

    @Test
    public void testFindById_notExists() {
        final CourtDTO courtDTO1 = testee.create(new CourtDTO(0L, null));

        final Optional<CourtDTO> court = testee.findById(courtDTO1.getId() + 1L);

        Assertions.assertTrue(court.isEmpty());
    }

    @Test
    public void testFindById_exists() {
        testee.create(new CourtDTO(0L, null));
        final CourtDTO courtDTO2 = testee.create(new CourtDTO(0L, null));

        final Optional<CourtDTO> court = testee.findById(courtDTO2.getId());

        Assertions.assertTrue(court.isPresent());
        Assertions.assertEquals(courtDTO2, court.get());
    }

    @Test
    public void testFindAll_emptyRepository() {
        final List<CourtDTO> courtDTOs = testee.findAll();

        Assertions.assertTrue(courtDTOs.isEmpty());
    }

    @Test
    public void testFindAll_exists() {
        final CourtDTO courtDTO1 = testee.create(new CourtDTO(0L, null));
        final CourtDTO courtDTO2 = testee.create(new CourtDTO(0L, null));

        final List<CourtDTO> courtDTOs = testee.findAll();

        Assertions.assertEquals(2, courtDTOs.size());
        Assertions.assertAll(
                () -> Assertions.assertTrue(courtDTOs.contains(courtDTO1)),
                () -> Assertions.assertTrue(courtDTOs.contains(courtDTO2))
        );
    }
}