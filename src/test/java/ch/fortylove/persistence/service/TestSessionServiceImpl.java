package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.dto.UserDTO;
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
        UserDTO expectedUserDTO = new UserDTO(0L, "firstname", "lastname", "email", "password", true, null, null);
        when(userService.findByEmail("testuser")).thenReturn(Optional.of(expectedUserDTO));

        // Act
        Optional<UserDTO> result = sessionService.getCurrentUser();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUserDTO, result.get());
    }

    @Test
    public void testGetCurrentUserWhenNoAuthenticatedUser() {
        // Arrange
        when(securityService.getAuthenticatedUser()).thenReturn(Optional.empty());

        // Act
        Optional<UserDTO> result = sessionService.getCurrentUser();

        // Assert
        assertFalse(result.isPresent());
    }
}