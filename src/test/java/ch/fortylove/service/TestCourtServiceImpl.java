package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Optional;

@SpringTest
class TestCourtServiceImpl extends ServiceTest {

    @Nonnull private final CourtService testee;

    @Autowired
    public TestCourtServiceImpl(@Nonnull final CourtService testee) {
        this.testee = testee;
    }

    @Test
    public void testCreate() {
        final Court createdCourt = testee.create(new Court(CourtType.CLAY, false));

        final Optional<Court> foundCourt = testee.findById(createdCourt.getId());
        Assertions.assertTrue(foundCourt.isPresent());
        Assertions.assertEquals(createdCourt, foundCourt.get());
    }

    @Test
    public void testFindById_notExists() {
        final Court createdCourt = testee.create(new Court(CourtType.CLAY, false));

        final Optional<Court> foundCourt = testee.findById(createdCourt.getId() + 1L);

        Assertions.assertTrue(foundCourt.isEmpty());
    }

    @Test
    public void testFindById_exists() {
        testee.create(new Court(CourtType.CLAY, false));
        final Court createdCourt = testee.create(new Court(CourtType.CLAY, false));

        final Optional<Court> foundCourt = testee.findById(createdCourt.getId());

        Assertions.assertTrue(foundCourt.isPresent());
        Assertions.assertEquals(createdCourt, foundCourt.get());
    }
}