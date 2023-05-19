package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.repository.RoleRepository;
import ch.fortylove.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

@Component
@Profile("!production")
public class UserSetupData {

    @Nonnull private final UserRepository userRepository;
    @Nonnull private final RoleRepository roleRepository;
    @Nonnull private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserSetupData(@Nonnull final UserRepository userRepository,
                         @Nonnull final RoleRepository roleRepository,
                         @Nonnull final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        return Collections.singletonList(roleRepository.findByName(Role.ROLE_ADMIN));
    }

    @Nonnull
    private Collection<Role> getStaffRole() {
        return Collections.singletonList(roleRepository.findByName(Role.ROLE_STAFF));
    }

    @Nonnull
    private Collection<Role> getUserRole() {
        return Collections.singletonList(roleRepository.findByName(Role.ROLE_USER));
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
        }
        user.setRoles(roles);
        userRepository.save(user);
    }
}