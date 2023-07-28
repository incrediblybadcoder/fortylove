package ch.fortylove.security;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.service.UserService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.Nonnull;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@SpringComponent
public class AuthenticationService {
    @Nonnull private final AuthenticationContext authenticationContext;
    @Nonnull private final UserService userService;
    @Nonnull private final UserFactory userFactory;

    public AuthenticationService(@Nonnull final AuthenticationContext authenticationContext, @Nonnull final UserService userService, final UserFactory userFactory) {
        this.authenticationContext = authenticationContext;
        this.userService = userService;
        this.userFactory = userFactory;
    }

    @Nonnull
    public Optional<UserDetails> getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class);
    }

    public void logout() {
        authenticationContext.logout();
    }

    @Nonnull
    public Optional<User> getCurrentUser() {
        final Optional<UserDetails> authenticatedUser = getAuthenticatedUser();
        if (authenticatedUser.isEmpty()) {
            return Optional.empty();
        }
        final String username = authenticatedUser.get().getUsername();
        return userService.findByEmail(username);
    }

    /**
     * Erstellt und registriert einen neuen Benutzer, wenn der übergebene E-Mail-Wert nicht bereits vorhanden ist.
     *
     * @param firstName     Der Vorname des Benutzers. Es wird erwartet, dass dies ein nicht-leerer String ist.
     * @param lastName      Der Nachname des Benutzers. Es wird erwartet, dass dies ein nicht-leerer String ist.
     * @param email         Die E-Mail des Benutzers. Es wird erwartet, dass dies ein eindeutiger und nicht-leerer String ist.
     * @param plainPassword Das Passwort des Benutzers im Klartext. Es wird erwartet, dass dies ein nicht-leerer String ist.
     *                      Beachte, dass das Passwort intern verschlüsselt und sicher gespeichert wird.
     *
     * @return true, wenn der Benutzer erfolgreich registriert wurde, d.h. es gab noch keinen anderen Benutzer mit der gleichen E-Mail.
     *         false, wenn die Registrierung nicht erfolgreich war, d.h. es gibt bereits einen Benutzer mit der gleichen E-Mail.
     */
    public boolean register(@Nonnull String firstName, @Nonnull String lastName, @Nonnull String email, @Nonnull  String plainPassword) {
        final Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty()) {
            userService.create(userFactory.newDefaultUser(firstName, lastName, email, plainPassword));
            return true;
        } else {
            return false;
        }
    }
}