package ch.fortylove.security;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Nonnull;
import java.util.Optional;

@SpringComponent
public interface SecurityService {

    @Nonnull
    Optional<UserDetails> getAuthenticatedUser();

    void logout();
}
