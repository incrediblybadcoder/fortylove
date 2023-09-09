package ch.fortylove.persistence.entity.factory;

import ch.fortylove.persistence.entity.AuthenticationDetails;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.UserStatus;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
public class UserFactory {

    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserFactory(@Nonnull final PlayerStatusService playerStatusService,
                       @Nonnull final RoleService roleService,
                       @Nonnull final PasswordEncoder passwordEncoder) {
        this.playerStatusService = playerStatusService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Erstellt einen neuen Standardbenutzer. Nachdem dieser Benutzer erstellt wurde,
     * muss sichergestellt werden, dass eine E-Mail an diesen Benutzer gesendet wird,
     * welche einen Aktivierungscode enthält.
     *
     * @return Ein neuer User-Objekt mit Standardwerten
     */
    @Nonnull
    public User newEmptyGuestUser(final boolean enabled) {
        return newGuestUser("", "", "", "", enabled);
    }

    /**
     * Erstellt einen neuen Standardbenutzer. Nachdem dieser Benutzer erstellt wurde,
     * muss sichergestellt werden, dass eine E-Mail an diesen Benutzer gesendet wird,
     * welche einen Aktivierungscode enthält.
     *
     * @param firstName Vorname des Benutzers
     * @param lastName Nachname des Benutzers
     * @param email E-Mail-Adresse des Benutzers
     * @param plainPassword Klartext-Passwort des Benutzers
     * @return Ein neuer User-Objekt mit Standardwerten
     */
    @Nonnull
    public User newGuestUser(@Nonnull final String firstName,
                             @Nonnull final String lastName,
                             @Nonnull final String email,
                             @Nonnull final String plainPassword,
                             final boolean enabled) {
        final AuthenticationDetails authenticationDetails = getAuthenticationDetailsWithEncryption(plainPassword);
        return new User(firstName, lastName, email, authenticationDetails, UserStatus.GUEST, enabled, roleService.getDefaultGuestRoles(), playerStatusService.getDefaultGuestPlayerStatus());
    }

    @Nonnull
    public User newAdminUser(@Nonnull final String firstName,
                             @Nonnull final String lastName,
                             @Nonnull final String email,
                             @Nonnull final String plainPassword) {
        final AuthenticationDetails authenticationDetails = getAuthenticationDetailsWithEncryption(plainPassword);
        return new User(firstName, lastName, email, authenticationDetails, UserStatus.MEMBER, true, roleService.getDefaultAdminRoles(), playerStatusService.getDefaultAdminPlayerStatus());
    }

    @Nonnull
    public User newDevAdminUser(@Nonnull final String firstName,
                                @Nonnull final String lastName,
                                @Nonnull final String email,
                                @Nonnull final String encryptedPassword) {
        final AuthenticationDetails authenticationDetails = new AuthenticationDetails(encryptedPassword, "");
        return new User(firstName, lastName, email, authenticationDetails, UserStatus.MEMBER, true, roleService.getDefaultAdminRoles(), playerStatusService.getDefaultAdminPlayerStatus());
    }

    @Nonnull
    private AuthenticationDetails getAuthenticationDetailsWithEncryption(@Nonnull final String plainPassword) {
        final String encryptedPassword = passwordEncoder.encode(plainPassword);
        return new AuthenticationDetails(encryptedPassword, "");
    }
}
