package ch.fortylove.persistence.entity.factory;

import ch.fortylove.persistence.entity.AuthenticationDetails;
import ch.fortylove.persistence.entity.User;
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

    @Nonnull
    public User newEmptyDefaultUser() {
        return newDefaultUser("", "", "", "");
    }

    @Nonnull
    public User newDefaultUser(@Nonnull final String firstName,
                               @Nonnull final String lastName,
                               @Nonnull final String email,
                               @Nonnull final String plainPassword) {
        final String encryptedPassword = passwordEncoder.encode(plainPassword);
        final AuthenticationDetails authenticationDetails = new AuthenticationDetails(encryptedPassword, "");
        return new User(firstName, lastName, email, authenticationDetails, true, roleService.getDefaultNewUserRoles(), playerStatusService.getDefaultNewUserPlayerStatus());
    }
}
