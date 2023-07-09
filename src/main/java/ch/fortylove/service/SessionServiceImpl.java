package ch.fortylove.service;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.security.SecurityService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    @Nonnull private final UserService userService;
    @Nonnull private final SecurityService securityService;

    public SessionServiceImpl(@Nonnull final UserService userService,
                              @Nonnull final SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @Nonnull
    @Override
    public Optional<User> getCurrentUser() {
        final Optional<UserDetails> authenticatedUser = securityService.getAuthenticatedUser();
        if (authenticatedUser.isEmpty()) {
            return Optional.empty();
        }
        final String username = authenticatedUser.get().getUsername();
        return userService.findByEmail(username);
    }
}
