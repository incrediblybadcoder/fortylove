package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.repository.UserRepository;
import ch.fortylove.persistence.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Profile({"h2", "develop", "local"})
public class UserSetupData {

    @Nonnull private final UserRepository userRepository;
    @Nonnull private final RoleService roleService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSetupData(@Nonnull final UserRepository userRepository,
                         @Nonnull final RoleService roleService,
                         @Nonnull final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
    private Collection<Role> getAdminRole() {
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(Role.ROLE_ADMIN);
        role.ifPresent(roles::add);

        return roles;
    }

    @Nonnull
    private Collection<Role> getStaffRole() {
        final List<Role> roles = new ArrayList<>();
        final Optional<Role> role = roleService.findByName(Role.ROLE_STAFF);
        role.ifPresent(roles::add);

        return roles;
    }

    @Nonnull
    private Collection<Role> getUserRole() {
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
                              @Nonnull final Collection<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}