package ch.fortylove.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public interface SecurityService {

    @Nonnull
    UserDetails getAuthenticatedUser();

    void logout();
}
