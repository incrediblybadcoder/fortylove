package ch.fortylove.persistence.entity.factory;

import ch.fortylove.persistence.entity.AuthenticationDetails;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.UserSettings;
import ch.fortylove.persistence.entity.UserStatus;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@SpringComponent
public class UserFactory {

    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final AuthenticationDetailsFactory authenticationDetailsFactory;
    @Nonnull private final UserSettingsFactory userSettingsFactory;

    @Autowired
    public UserFactory(@Nonnull final PlayerStatusService playerStatusService,
                       @Nonnull final RoleService roleService,
                       @Nonnull final AuthenticationDetailsFactory authenticationDetailsFactory,
                       @Nonnull final UserSettingsFactory userSettingsFactory) {
        this.playerStatusService = playerStatusService;
        this.roleService = roleService;
        this.authenticationDetailsFactory = authenticationDetailsFactory;
        this.userSettingsFactory = userSettingsFactory;
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
     * @param firstName     Vorname des Benutzers
     * @param lastName      Nachname des Benutzers
     * @param email         E-Mail-Adresse des Benutzers
     * @param plainPassword Klartext-Passwort des Benutzers
     * @return Ein neuer User-Objekt mit Standardwerten
     */
    @Nonnull
    public User newGuestUser(@Nonnull final String firstName,
                             @Nonnull final String lastName,
                             @Nonnull final String email,
                             @Nonnull final String plainPassword,
                             final boolean enabled) {
        final AuthenticationDetails authenticationDetails = authenticationDetailsFactory.newAuthenticationDetails(plainPassword);
        return newUser(firstName, lastName, email, authenticationDetails, UserStatus.GUEST, enabled, roleService.getDefaultGuestRoles(), playerStatusService.getDefaultGuestPlayerStatus());
    }

    @Nonnull
    public User newAdminUser(@Nonnull final String firstName,
                             @Nonnull final String lastName,
                             @Nonnull final String email,
                             @Nonnull final String plainPassword) {
        final AuthenticationDetails authenticationDetails = authenticationDetailsFactory.newAuthenticationDetails(plainPassword);
        return newUser(firstName, lastName, email, authenticationDetails, UserStatus.MEMBER, true, roleService.getDefaultAdminRoles(), playerStatusService.getDefaultAdminPlayerStatus());
    }

    @Nonnull
    public User newDevAdminUser(@Nonnull final String firstName,
                                @Nonnull final String lastName,
                                @Nonnull final String email,
                                @Nonnull final String encryptedPassword) {
        final AuthenticationDetails authenticationDetails = new AuthenticationDetails(encryptedPassword);
        return newUser(firstName, lastName, email, authenticationDetails, UserStatus.MEMBER, true, roleService.getDefaultAdminRoles(), playerStatusService.getDefaultAdminPlayerStatus());
    }

    @Nonnull
    private User newUser(@Nonnull final String firstName,
                         @Nonnull final String lastName,
                         @Nonnull final String email,
                         @Nonnull final AuthenticationDetails authenticationDetails,
                         @Nonnull final UserStatus userStatus,
                         final boolean enabled,
                         @Nonnull final Set<Role> roles,
                         @Nonnull final PlayerStatus playerStatus) {
        final UserSettings userSettings = userSettingsFactory.newUserSettings();
        return new User(firstName, lastName, email, authenticationDetails, userStatus, enabled, roles, playerStatus, userSettings);
    }
}
