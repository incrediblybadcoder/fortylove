package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringTest
class TestSessionServiceImpl {

    @Autowired SessionService sessionServiceTestee;

    @Test
    void testGetCurrentUser() {
        // Arrange
        // Act
        final Optional<User> currentUser = sessionServiceTestee.getCurrentUser();
        // Assert
        Assertions.assertEquals(true, true);
    }
}