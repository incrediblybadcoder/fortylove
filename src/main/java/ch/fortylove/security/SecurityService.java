package ch.fortylove.security;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.service.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Nonnull;
import java.util.Optional;

@SpringComponent
public class SecurityService {

    @Nonnull private final AuthenticationContext authenticationContext;
    @Nonnull private final UserService userService;

    @Autowired
    public SecurityService(@Nonnull final AuthenticationContext authenticationContext,
                           @Nonnull final UserService userService) {
        this.authenticationContext = authenticationContext;
        this.userService = userService;
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

    public void logout() {
        authenticationContext.logout();
    }

    @Nonnull
    private Optional<UserDetails> getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class);
    }
}
