package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.security.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringTest
public class TestSessionServiceImpl {

    private SessionServiceImpl sessionService;
    private SecurityService securityService;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        securityService = mock(SecurityService.class);
        userService = mock(UserService.class);
        sessionService = new SessionServiceImpl(userService, securityService);
    }

    @Test
    public void testGetCurrentUser() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(securityService.getAuthenticatedUser()).thenReturn(Optional.of(userDetails));
        User expectedUser = new User(0L, "firstname", "lastname", "email", "password", true, null, null, null, null);
        when(userService.findByEmail("testuser")).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = sessionService.getCurrentUser();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
    }

    @Test
    public void testGetCurrentUserWhenNoAuthenticatedUser() {
        // Arrange
        when(securityService.getAuthenticatedUser()).thenReturn(Optional.empty());

        // Act
        Optional<User> result = sessionService.getCurrentUser();

        // Assert
        assertFalse(result.isPresent());
    }
}