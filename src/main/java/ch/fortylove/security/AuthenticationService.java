package ch.fortylove.security;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import ch.fortylove.service.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.Nonnull;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@SpringComponent
public class AuthenticationService {

    @Nonnull private final AuthenticationContext authenticationContext;

    @Nonnull private final UserService userService;
    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final RoleService roleService;

    public AuthenticationService(@Nonnull final AuthenticationContext authenticationContext, @Nonnull final UserService userService, final PlayerStatusService playerStatusService, final RoleService roleService) {
        this.authenticationContext = authenticationContext;
        this.userService = userService;
        this.playerStatusService = playerStatusService;
        this.roleService = roleService;
    }

    @Nonnull
    public Optional<UserDetails> getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class);
    }

    public void logout() {
        authenticationContext.logout();
    }

    @Nonnull
    public Optional<User> getCurrentUser() {
        final Optional<UserDetails> authenticatedUser = getAuthenticatedUser();
        if (authenticatedUser.isEmpty()) {
            return Optional.empty();
        }
        final String username = authenticatedUser.get().getUsername();
        return userService.findByEmail(username);
    }

    public void register(String email, String firstName, String lastName, String plainPassword) throws DuplicateRecordException{
        User newUser = new User(firstName, lastName, email, "", true, roleService.getDefaultNewUserRoles(), playerStatusService.getDefaultNewUserPlayerStatus());
        newUser.setEncryptedPassword(SecurityConfiguration.getPasswordEncoder().encode(plainPassword));
        userService.create(newUser);
    }

    public boolean checkPassword(@Nonnull final User user, String plainPassword) {
        return user.getEncryptedPassword().equals(SecurityConfiguration.getPasswordEncoder().encode(plainPassword));
    }
}