package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.PlayerStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringTest
public class TestPlayerStatusServiceImpl {

    @Autowired
    PlayerStatusService testee;

    @Test
    public void testCreate() {
        //Arange
        final String name = "playerStatusName";
        //Act
        final PlayerStatus createdPlayerStatus = testee.create(new PlayerStatus(name, 2, 7));
        //Assert
        Assertions.assertTrue(testee.findByName(name).isPresent());
        Assertions.assertEquals(createdPlayerStatus, testee.findByName(name).get());
    }

    @Test
    public void testFindByName_notExists() {
        //Arange
        final String name = "playerStatusName1";
        //Act
        final PlayerStatus createdPlayerStatus = testee.create(new PlayerStatus(name, 2, 7));
        final Optional<PlayerStatus> playerStatusName2 = testee.findByName("playerStatusName2");
        //Assert
        Assertions.assertTrue(playerStatusName2.isEmpty());
    }

    @Test
    public void testFindByName_exists() {
        //Arange
        final String name = "playerStatusName1";
        //Act
        final PlayerStatus createdPlayerStatus = testee.create(new PlayerStatus(name, 2, 7));
        final Optional<PlayerStatus> playerStatusName = testee.findByName("playerStatusName1");
        //Assert
        Assertions.assertTrue(playerStatusName.isPresent());
        Assertions.assertEquals(createdPlayerStatus, playerStatusName.get());
    }
}
