package ch.fortylove.configuration.setupdata.data;

import ch.fortylove.configuration.setupdata.SetupData;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.service.UserService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SetupData
public class DefaultUserSetupData {

    public static final String DEVELOP_USER = "fortylove.untervaz@gmail.com";

    @Nonnull private final UserService userService;
    @Nonnull private final UserFactory userFactory;

    @Autowired
    public DefaultUserSetupData(@Nonnull final UserService userService,
                                @Nonnull final UserFactory userFactory) {
        this.userService = userService;
        this.userFactory = userFactory;
    }

    /**
     * Damit werden die Default User, welche es auf der App immer geben soll, erstellt.
     */
    public void createDefaultUser() {
        // Der DEVELOP_USER ist gedacht, um initial die App zu konfigurieren.
        // Der DEVELOP_USER soll in der App nicht sichtbar sein.
        createUserIfNotFound(DEVELOP_USER, "Forty", "Love", "$2a$10$lSFt1KQWpHLwCRyQaXjG3e16zkZu3tHCy1cv1LofhwWcYdGucj9xS");
    }

    @Transactional
    void createUserIfNotFound(@Nonnull final String email,
                              @Nonnull final String firstName,
                              @Nonnull final String lastName,
                              @Nonnull final String password) {
        final Optional<User> existingUser = userService.findByEmail(email);

        if (existingUser.isEmpty()) {
            final User user = userFactory.newDevAdmin(firstName, lastName, email, password);
            userService.create(user);
        }
    }
}