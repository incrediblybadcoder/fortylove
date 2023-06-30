package ch.fortylove.devsetupdata.data;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.RoleService;
import ch.fortylove.persistence.service.UserService;
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
    @Nonnull private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSetupData(@Nonnull final UserService userService,
                         @Nonnull final RoleService roleService,
                         @Nonnull final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUsers() {
        createUserIfNotFound("admin@fortylove.ch", "admin", "admin", "password", getAdminRole());
        createUserIfNotFound("staff@fortylove.ch", "staff", "staff", "password", getStaffRole());

        createUserIfNotFound("marco@fortylove.ch", "Marco", "Solombrino", "password", getUserRole());
        createUserIfNotFound("jonas@fortylove.ch", "Jonas", "Cahenzli", "password", getUserRole());
        createUserIfNotFound("daniel@fortylove.ch", "Daniel", "Tobler", "password", getUserRole());
    }

    @Nonnull
    private List<Role> getAdminRole() {
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(Role.ROLE_ADMIN);
        role.ifPresent(roles::add);

        return roles;
    }

    @Nonnull
    private List<Role> getStaffRole() {
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(Role.ROLE_STAFF);
        role.ifPresent(roles::add);

        return roles;
    }

    @Nonnull
    private List<Role> getUserRole() {
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(Role.ROLE_USER);
        role.ifPresent(roles::add);

        return roles;
    }

    @Transactional
    void createUserIfNotFound(@Nonnull final String email,
                              @Nonnull final String firstName,
                              @Nonnull final String lastName,
                              @Nonnull final String password,
                              @Nonnull final List<Role> Roles) {
        final Optional<User> user = userService.findByEmail(email);

        if (user.isEmpty()) {
            userService.create(new User(0L, firstName, lastName, email, passwordEncoder.encode(password), true, Roles, null));
        }
    }
}