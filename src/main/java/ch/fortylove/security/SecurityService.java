package ch.fortylove.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Optional;

@Component
public interface SecurityService {

    @Nonnull
    Optional<UserDetails> getAuthenticatedUser();

    void logout();
}
