package ch.fortylove.security;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Nonnull;
import java.util.Optional;

@SpringComponent
public class SecurityServiceImpl implements SecurityService {

    @Nonnull private final AuthenticationContext authenticationContext;

    public SecurityServiceImpl(@Nonnull final AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    @Nonnull
    @Override
    public Optional<UserDetails> getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class);
    }

    @Override
    public void logout() {
        authenticationContext.logout();
    }
}