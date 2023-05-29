package ch.fortylove.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class SecurityServiceImpl implements SecurityService {

    @Nonnull private final AuthenticationContext authenticationContext;

    public SecurityServiceImpl(@Nonnull final AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    @Nonnull
    public UserDetails getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class).get();
    }

    public void logout() {
        authenticationContext.logout();
    }
}