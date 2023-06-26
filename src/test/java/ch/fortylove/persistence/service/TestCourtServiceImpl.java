package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.dto.Court;
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
        final Court createdCourt = testee.create(new Court(0L, null));

        Assertions.assertEquals(1, testee.findAll().size());
        Assertions.assertEquals(createdCourt, testee.findAll().get(0));
    }

    @Test
    public void testFindById_notExists() {
        final Court court1 = testee.create(new Court(0L, null));

        final Optional<Court> court = testee.findById(court1.getId() + 1L);

        Assertions.assertTrue(court.isEmpty());
    }

    @Test
    public void testFindById_exists() {
        testee.create(new Court(0L, null));
        final Court court2 = testee.create(new Court(0L, null));

        final Optional<Court> court = testee.findById(court2.getId());

        Assertions.assertTrue(court.isPresent());
        Assertions.assertEquals(court2, court.get());
    }

    @Test
    public void testFindAll_emptyRepository() {
        final List<Court> courts = testee.findAll();

        Assertions.assertTrue(courts.isEmpty());
    }

    @Test
    public void testFindAll_exists() {
        final Court court1 = testee.create(new Court(0L, null));
        final Court court2 = testee.create(new Court(0L, null));

        final List<Court> courts = testee.findAll();

        Assertions.assertEquals(2, courts.size());
        Assertions.assertAll(
                () -> Assertions.assertTrue(courts.contains(court1)),
                () -> Assertions.assertTrue(courts.contains(court2))
        );
    }
}