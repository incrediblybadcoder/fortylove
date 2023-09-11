package ch.fortylove.persistence.entity.factory;

import ch.fortylove.persistence.entity.AuthenticationDetails;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
public class AuthenticationDetailsFactory {

    @Nonnull private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationDetailsFactory(@Nonnull final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Nonnull
    public AuthenticationDetails newAuthenticationDetails(@Nonnull final String plainPassword) {
        final String encryptedPassword = passwordEncoder.encode(plainPassword);
        return new AuthenticationDetails(encryptedPassword);
    }
}
