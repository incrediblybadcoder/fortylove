package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Court;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringTest
class TestCourtServiceImpl {

    @Autowired CourtService testee;

    @Test
    public void testCreate() {
        final Court createdCourt = testee.create(new Court(0L, null));

        Assertions.assertTrue(testee.findById(createdCourt.getId()).isPresent());
        Assertions.assertEquals(createdCourt, testee.findById(createdCourt.getId()).get());
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
}