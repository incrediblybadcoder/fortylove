package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.configuration.setupdata.data.PlayerStatusSetupData;
import ch.fortylove.persistence.entity.AuthenticationDetails;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.UserStatus;
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
    @Nonnull public static final String ACTIVE = "aktiv@fortylove.ch";
    @Nonnull public static final String PASSIVE = "passiv@fortylove.ch";
    @Nonnull public static final String TOURNAMENT = "turnier@fortylove.ch";
    @Nonnull public static final String INACTIVE = "inaktiv@fortylove.ch";
    @Nonnull public static final String GUEST1 = "gast1@fortylove.ch";
    @Nonnull public static final String GUEST2 = "gast2@fortylove.ch";
    @Nonnull public static final String GUEST3 = "gast3@fortylove.ch";

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
        createUserIfNotFound(ADMIN, "Admin", "Admin", "password", UserStatus.MEMBER, getAdminRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound(STAFF, "Staff", "Staff", "password", UserStatus.MEMBER, getStaffRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);

        createUserIfNotFound(USER1, "Marco", "Solombrino", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound(USER2, "Jonas", "Cahenzli", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound(USER3, "Daniel", "Tobler", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);

        createUserIfNotFound(ACTIVE, "Aktiv", "Aktiv", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.ACTIVE), true);
        createUserIfNotFound(PASSIVE, "Passiv", "Passiv", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.PASSIVE), true);
        createUserIfNotFound(TOURNAMENT, "Turnier", "Turnier", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.TOURNAMENT), true);
        createUserIfNotFound(INACTIVE, "Inaktiv", "Inaktiv", "password", UserStatus.MEMBER, getUserRole(), getPlayerStatus(PlayerStatusSetupData.INACTIVE), false);
        createUserIfNotFound(GUEST1, "Hans", "Wurst", "password", UserStatus.GUEST, getUserRole(), getPlayerStatus(PlayerStatusSetupData.GUEST), true);
        createUserIfNotFound(GUEST2, "Ramsi", "Hartmann", "password", UserStatus.GUEST_PENDING, getUserRole(), getPlayerStatus(PlayerStatusSetupData.GUEST), true);
        createUserIfNotFound(GUEST3, "Pauli", "Paulson", "password", UserStatus.GUEST_PENDING, getUserRole(), getPlayerStatus(PlayerStatusSetupData.GUEST), true);
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
                              @Nonnull final UserStatus userStatus,
                              @Nonnull final Set<Role> Roles,
                              @Nonnull final PlayerStatus playerStatus,
                              final boolean activationStatus) {
        final Optional<User> existingUser = userService.findByEmail(email);

        if (existingUser.isEmpty()) {
            final AuthenticationDetails authenticationDetails = new AuthenticationDetails(passwordEncoder.encode(password));
            final User user = new User(firstName, lastName, email, authenticationDetails, userStatus, activationStatus, Roles, playerStatus);
            authenticationDetails.setUser(user);
            userService.create(user);
        }
    }
}