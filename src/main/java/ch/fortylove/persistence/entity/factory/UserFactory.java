package ch.fortylove.persistence.entity.factory;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import jakarta.annotation.Nonnull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    public UserFactory(final PlayerStatusService playerStatusService, final RoleService roleService, final PasswordEncoder passwordEncoder) {
        this.playerStatusService = playerStatusService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User newDefaultUser() {
        return new User("", "", "", "", true, roleService.getDefaultNewUserRoles(), playerStatusService.getDefaultNewUserPlayerStatus());
    }

    public User newDefaultUser(String firstName, String lastName, String email, String plainPassword) {
        return new User(firstName, lastName, email, passwordEncoder.encode(plainPassword), true, roleService.getDefaultNewUserRoles(), playerStatusService.getDefaultNewUserPlayerStatus());
    }
}
