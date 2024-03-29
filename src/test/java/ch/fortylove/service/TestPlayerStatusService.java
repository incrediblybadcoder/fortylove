package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.PlayerStatus;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringTest
public class TestPlayerStatusService extends ServiceTest {

    @Nonnull private final PlayerStatusService testee;

    @Autowired
    public TestPlayerStatusService(@Nonnull final PlayerStatusService testee) {
        this.testee = testee;
    }

    @Test
    public void testCreate() {
        //Arrange
        final String name = "playerStatusName";
        //Act
        final PlayerStatus createdPlayerStatus = testee.create(new PlayerStatus(name, 2, 7)).getData().get();
        //Assert
        final Optional<PlayerStatus> foundPlayerStatus = testee.findByName(name);
        Assertions.assertTrue(foundPlayerStatus.isPresent());
        Assertions.assertEquals(createdPlayerStatus, foundPlayerStatus.get());
    }

    @Test
    public void testFindByName_notExists() {
        //Arrange
        final String name = "playerStatusName1";
        //Act
        testee.create(new PlayerStatus(name, 2, 7));
        final Optional<PlayerStatus> foundPlayerStatus = testee.findByName("playerStatusName2");
        //Assert
        Assertions.assertTrue(foundPlayerStatus.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        //Arrange
        final String name = "playerStatusName";
        //Act
        final PlayerStatus createdPlayerStatus = testee.create(new PlayerStatus(name, 2, 7)).getData().get();
        final Optional<PlayerStatus> foundPlayerStatus = testee.findByName(name);
        //Assert
        Assertions.assertTrue(foundPlayerStatus.isPresent());
        Assertions.assertEquals(createdPlayerStatus, foundPlayerStatus.get());
    }
}
