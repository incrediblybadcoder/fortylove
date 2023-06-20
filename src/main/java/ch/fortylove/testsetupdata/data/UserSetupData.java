package ch.fortylove.testsetupdata.data;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.RoleService;
import ch.fortylove.persistence.service.UserService;
import ch.fortylove.testsetupdata.TestSetupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestSetupData
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
        createUserIfNotFound("admin", "admin", "admin", "password", getAdminRole());
        createUserIfNotFound("staff", "staff", "staff", "password", getStaffRole());

        createUserIfNotFound("marco", "Marco", "Solombrino", "password", getUserRole());
        createUserIfNotFound("jonas", "Jonas", "Cahenzli", "password", getUserRole());
        createUserIfNotFound("daniel", "Daniel", "Tobler", "password", getUserRole());
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
                              @Nonnull final List<Role> roles) {
        final Optional<User> user = userService.findByEmail(email);

        if (user.isEmpty()) {
            userService.create(new User(firstName, lastName, passwordEncoder.encode(password), email, roles));
        }
    }
}