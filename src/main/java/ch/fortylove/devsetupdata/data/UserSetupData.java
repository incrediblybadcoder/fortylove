package ch.fortylove.devsetupdata.data;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.service.PlayerStatusService;
import ch.fortylove.persistence.service.RoleService;
import ch.fortylove.persistence.service.UserService;
import ch.fortylove.persistence.setupdata.data.PlayerStatusSetupData;
import ch.fortylove.persistence.setupdata.data.RoleSetupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DevSetupData
public class UserSetupData {

    @Nonnull private final UserService userService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSetupData(@Nonnull final UserService userService,
                         @Nonnull final RoleService roleService,
                         @Nonnull final PlayerStatusService playerStatus,
                         @Nonnull final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.playerStatusService = playerStatus;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUsers() {
        createUserIfNotFound("admin@fortylove.ch", "admin", "admin", "password", getAdminRole(), getPlayerStatus(PlayerStatusSetupData.AKTIV));
        createUserIfNotFound("staff@fortylove.ch", "staff", "staff", "password", getStaffRole(), getPlayerStatus(PlayerStatusSetupData.AKTIV));

        createUserIfNotFound("marco@fortylove.ch", "Marco", "Solombrino", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.AKTIV));
        createUserIfNotFound("jonas@fortylove.ch", "Jonas", "Cahenzli", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.AKTIV));
        createUserIfNotFound("daniel@fortylove.ch", "Daniel", "Tobler", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.AKTIV));

        createUserIfNotFound("passivPlayer@fortylove.ch", "Passiv", "Player", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.PASSIV));
        createUserIfNotFound("turnierspielerPlayerr@fortylove.ch", "Turnier Spieler", "Player", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.TURNIER));
        createUserIfNotFound("inaktivPlayer@fortylove.ch", "Inaktiv", "Player", "password", getUserRole(), getPlayerStatus(PlayerStatusSetupData.INAKTIV));
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
    private List<Role> getAdminRole() {
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(RoleSetupData.ROLE_ADMIN);
        role.ifPresent(roles::add);

        return roles;
    }

    @Nonnull
    private List<Role> getStaffRole() {
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(RoleSetupData.ROLE_STAFF);
        role.ifPresent(roles::add);

        return roles;
    }

    @Nonnull
    private List<Role> getUserRole() {
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(RoleSetupData.ROLE_USER);
        role.ifPresent(roles::add);

        return roles;
    }

    @Transactional
    void createUserIfNotFound(@Nonnull final String email,
                              @Nonnull final String firstName,
                              @Nonnull final String lastName,
                              @Nonnull final String password,
                              @Nonnull final List<Role> Roles,
                              @Nonnull final PlayerStatus playerStatus) {
        final Optional<User> user = userService.findByEmail(email);

        if (user.isEmpty()) {
            userService.create(new User(firstName, lastName, email, passwordEncoder.encode(password), true, Roles, playerStatus));
        }
    }
}