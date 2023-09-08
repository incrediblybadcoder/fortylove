package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.configuration.setupdata.data.PlayerStatusSetupData;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@DevSetupData
public class UserDevSetupData {

    @Nonnull public static final String ADMIN = "admin@fortylove.ch";
    @Nonnull public static final String STAFF = "staff@fortylove.ch";
    @Nonnull public static final String USER1 = "marco@fortylove.ch";
    @Nonnull public static final String USER2 = "jonas@fortylove.ch";
    @Nonnull public static final String USER3 = "daniel@fortylove.ch";
    @Nonnull public static final String AKTIV = "aktiv@fortylove.ch";
    @Nonnull public static final String PASSIV = "passiv@fortylove.ch";
    @Nonnull public static final String TURNIER = "turnier@fortylove.ch";
    @Nonnull public static final String INAKIV = "inaktiv@fortylove.ch";

    @Nonnull private final UserService userService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDevSetupData(@Nonnull final UserService userService, @Nonnull final RoleService roleService,
                            @Nonnull final PlayerStatusService playerStatus,
                            @Nonnull final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.playerStatusService = playerStatus;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUsers() {
        createUserIfNotFound(ADMIN, "Admin", "Admin", "password", getAdminRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound(STAFF, "Staff", "Staff", "password", getStaffRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);

        createUserIfNotFound(USER1, "Marco", "Solombrino", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound(USER2, "Jonas", "Cahenzli", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound(USER3, "Daniel", "Tobler", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);

        createUserIfNotFound(AKTIV, "Aktiv", "Aktiv", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound(PASSIV, "Passiv", "Passiv", "password", getUserRole(), getPlayerStatus(PlayerStatusDevSetupData.PASSIV), true);
        createUserIfNotFound(TURNIER, "Turnier", "Turnier", "password", getUserRole(), getPlayerStatus(PlayerStatusDevSetupData.TURNIER), true);
        createUserIfNotFound(INAKIV, "Inaktiv", "Inaktiv", "password", getUserRole(), getPlayerStatus(PlayerStatusDevSetupData.INAKTIV), false);
    }

    @Nonnull
    private PlayerStatus getPlayerStatus(@Nonnull final String name) {
        final Optional<PlayerStatus> playerStatus = playerStatusService.findByName(name);
        if (playerStatus.isPresent()) {
            return playerStatus.get();
        }
        throw new RecordNotFoundException("PlayerStatus " + name + " not found");
    }

    @Nonnull
    private Set<Role> getAdminRole() {
        return roleService.getDefaultAdminRoles();
    }

    @Nonnull
    private Set<Role> getStaffRole() {
        return roleService.getDefaultStaffRoles();
    }

    @Nonnull
    private Set<Role> getUserRole() {
        return roleService.getDefaultUserRoles();
    }

    @Transactional
    void createUserIfNotFound(@Nonnull final String email,
                              @Nonnull final String firstName,
                              @Nonnull final String lastName,
                              @Nonnull final String password,
                              @Nonnull final Set<Role> Roles,
                              @Nonnull final PlayerStatus playerStatus,
                              final boolean activationStatus) {
        final Optional<User> existingUser = userService.findByEmail(email);

        if (existingUser.isEmpty()) {
            final AuthenticationDetails authenticationDetails = new AuthenticationDetails(passwordEncoder.encode(password), "");
            final User user = new User(firstName, lastName, email, authenticationDetails, activationStatus, Roles, playerStatus);
            authenticationDetails.setUser(user);
            userService.create(user);
        }
    }
}