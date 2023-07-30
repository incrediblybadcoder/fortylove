package ch.fortylove.security;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.service.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@SpringComponent
public class AuthenticationService {

    @Nonnull private final AuthenticationContext authenticationContext;
    @Nonnull private final UserService userService;

    @Autowired
    public AuthenticationService(@Nonnull final AuthenticationContext authenticationContext,
                                 @Nonnull final UserService userService) {
        this.authenticationContext = authenticationContext;
        this.userService = userService;
    }

    @Nonnull
    public Optional<User> getAuthenticatedUser() {
        final Optional<UserDetails> authenticatedUser = authenticationContext.getAuthenticatedUser(UserDetails.class);

        if (authenticatedUser.isEmpty()) {
            return Optional.empty();
        }
        final String username = authenticatedUser.get().getUsername();
        return userService.findByEmail(username);
    }

    public void logout() {
        authenticationContext.logout();
    }

}