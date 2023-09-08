package ch.fortylove.presentation.views.registration.events;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.views.registration.RegistrationForm;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class RegistrationEvent extends ComponentEvent<RegistrationForm> {
    @Nonnull private final User user;

    private final String plainPassword;

    public RegistrationEvent(RegistrationForm source, User user, String plainPassword) {
        super(source, false);
        this.user = user;
        this.plainPassword = plainPassword;
    }

    @Nonnull
    public User getUser() {
        return user;
    }
    public String getPlainPassword() {
        return plainPassword;
    }
}
