package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import ch.fortylove.persistence.entity.CourtType;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@SpringTest
class TestCourtService extends ServiceTest {

    @Nonnull private final CourtService testee;

    @Autowired
    public TestCourtService(@Nonnull final CourtService testee) {
        this.testee = testee;
    }

    @Test
    public void testCreate() {
        final Court createdCourt = testee.create(new Court(CourtType.CLAY, CourtIcon.ORANGE, 0, "name")).getData().get();

        final Optional<Court> foundCourt = testee.findById(createdCourt.getId());
        Assertions.assertTrue(foundCourt.isPresent());
        Assertions.assertEquals(createdCourt, foundCourt.get());
    }

    @Test
    public void testFindById_notExists() {
        final Court createdCourt = testee.create(new Court(CourtType.CLAY, CourtIcon.ORANGE, 0, "name")).getData().get();
        final UUID searchId = new UUID(0L, 0L);
        Assertions.assertNotEquals(createdCourt.getId(), searchId);

        final Optional<Court> foundCourt = testee.findById(searchId);

        Assertions.assertTrue(foundCourt.isEmpty());
    }

    @Test
    public void testFindById_exists() {
        final Court createdCourt = testee.create(new Court(CourtType.CLAY, CourtIcon.ORANGE, 0, "name")).getData().get();

        final Optional<Court> foundCourt = testee.findById(createdCourt.getId());

        Assertions.assertTrue(foundCourt.isPresent());
        Assertions.assertEquals(createdCourt, foundCourt.get());
    }
}