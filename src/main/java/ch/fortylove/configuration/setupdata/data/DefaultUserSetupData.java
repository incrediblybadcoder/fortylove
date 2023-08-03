package ch.fortylove.configuration.setupdata.data;

import ch.fortylove.configuration.setupdata.SetupData;
import ch.fortylove.persistence.entity.AuthenticationDetails;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import ch.fortylove.service.UserService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SetupData
public class DefaultUserSetupData {

    public static final String DEVELOP_USER = "fortylove.untervaz@gmail.com";
    @Nonnull private final UserService userService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final PlayerStatusService playerStatusService;

    @Autowired
    public DefaultUserSetupData(@Nonnull final UserService userService, final RoleService roleService, final PlayerStatusService playerStatusService) {
        this.userService = userService;
        this.roleService = roleService;
        this.playerStatusService = playerStatusService;
    }
    public void createDefaultUser() {
        createUserIfNotFound(DEVELOP_USER, "Forty", "Love", "$2a$10$lSFt1KQWpHLwCRyQaXjG3e16zkZu3tHCy1cv1LofhwWcYdGucj9xS", getAdminRole(), getPlayerStatus(PlayerStatusSetupData.AKTIV), true);
    }

    @Nonnull
    private Set<Role> getAdminRole() {
        final Set<Role> roles = new HashSet<>();
        final Optional<Role> role = roleService.findByName(RoleSetupData.ROLE_ADMIN);
        role.ifPresent(roles::add);

        return roles;
    }

    @Nonnull
    private PlayerStatus getPlayerStatus(@Nonnull final String name) {
        final Optional<PlayerStatus> playerStatus = playerStatusService.findByName(name);
        if (playerStatus.isPresent()) {
            return playerStatus.get();
        }
        throw new RecordNotFoundException("PlayerStatus " + name + " not found");
    }
    @Transactional
    void createUserIfNotFound(@Nonnull final String email,
                              @Nonnull final String firstName,
                              @Nonnull final String lastName,
                              @Nonnull final String password,
                              @Nonnull final Set<Role> Roles,
                              @Nonnull final PlayerStatus playerStatus,
                              @Nonnull final boolean activationStatus) {
        final Optional<User> existingUser = userService.findByEmail(email);

        if (existingUser.isEmpty()) {
            final AuthenticationDetails authenticationDetails = new AuthenticationDetails(password, "");
            final User user = new User(firstName, lastName, email, authenticationDetails, activationStatus, Roles, playerStatus);
            authenticationDetails.setUser(user);
            userService.create(user);
        }
    }
}