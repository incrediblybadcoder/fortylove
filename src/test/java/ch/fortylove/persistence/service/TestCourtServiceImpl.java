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
        final CourtDTO createdCourt = testee.create(new CourtDTO(0L, null));

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdCourt, testee.findAll().get(0));
    }

    @Test
    public void testFindById_notExists() {
        final CourtDTO court1 = testee.create(new CourtDTO(0L, null));

        final Optional<CourtDTO> court = testee.findById(court1.getId() + 1L);

        Assertions.assertTrue(court.isEmpty());
    }

    @Test
    public void testFindById_exists() {
        testee.create(new CourtDTO(0L, null));
        final CourtDTO court2 = testee.create(new CourtDTO(0L, null));

        final Optional<CourtDTO> court = testee.findById(court2.getId());

        Assertions.assertTrue(court.isPresent());
        Assertions.assertEquals(court2, court.get());
    }

    @Test
    public void testFindAll_emptyRepository() {
        final List<CourtDTO> courts = testee.findAll();

        Assertions.assertTrue(courts.isEmpty());
    }

    @Test
    public void testFindAll_exists() {
        final CourtDTO court1 = testee.create(new CourtDTO(0L, null));
        final CourtDTO court2 = testee.create(new CourtDTO(0L, null));

        final List<CourtDTO> courts = testee.findAll();

        Assertions.assertEquals(2, courts.size());
        Assertions.assertAll(
                () -> Assertions.assertTrue(courts.contains(court1)),
                () -> Assertions.assertTrue(courts.contains(court2))
        );
    }
}