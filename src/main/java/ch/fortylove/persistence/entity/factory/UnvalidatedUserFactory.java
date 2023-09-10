package ch.fortylove.persistence.entity.factory;

import ch.fortylove.persistence.entity.UnvalidatedUser;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
public class UnvalidatedUserFactory {

    @Nonnull private final PasswordEncoder passwordEncoder;

    @Autowired
    public UnvalidatedUserFactory(@Nonnull final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Nonnull
    public UnvalidatedUser newUnvalidatedUser() {
        return newUnvalidatedUser("", "", "", "" );
    }
    @Nonnull
    public UnvalidatedUser newUnvalidatedUser(@Nonnull final String firstName,
                                              @Nonnull final String lastName,
                                              @Nonnull final String email,
                                              @Nonnull final String plainPassword) {
        return new UnvalidatedUser(firstName, lastName, email, passwordEncoder.encode(plainPassword) );
    }
}
