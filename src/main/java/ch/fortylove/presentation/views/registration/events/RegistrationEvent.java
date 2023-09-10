package ch.fortylove.presentation.views.registration.events;

import ch.fortylove.persistence.entity.UnvalidatedUser;
import ch.fortylove.presentation.views.registration.RegistrationForm;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class RegistrationEvent extends ComponentEvent<RegistrationForm> {
    @Nonnull private final UnvalidatedUser unvalidatedUser;

    private final String plainPassword;

    public RegistrationEvent(RegistrationForm source, UnvalidatedUser unvalidatedUser, String plainPassword) {
        super(source, false);
        this.unvalidatedUser = unvalidatedUser;
        this.plainPassword = plainPassword;
    }

    @Nonnull
    public UnvalidatedUser getUnvalidatedUser() {
        return unvalidatedUser;
    }
    public String getPlainPassword() {
        return plainPassword;
    }
}
