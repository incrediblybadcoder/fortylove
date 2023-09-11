package ch.fortylove.presentation.views.account.registration.events;

import ch.fortylove.persistence.entity.UnvalidatedUser;
import ch.fortylove.presentation.views.account.registration.RegistrationForm;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class RegistrationEvent extends ComponentEvent<RegistrationForm> {

    @Nonnull private final UnvalidatedUser unvalidatedUser;
    @Nonnull private final String plainPassword;

    public RegistrationEvent(@Nonnull final RegistrationForm source,
                             @Nonnull final UnvalidatedUser unvalidatedUser,
                             @Nonnull final String plainPassword) {
        super(source, false);
        this.unvalidatedUser = unvalidatedUser;
        this.plainPassword = plainPassword;
    }

    @Nonnull
    public UnvalidatedUser getUnvalidatedUser() {
        return unvalidatedUser;
    }

    @Nonnull
    public String getPlainPassword() {
        return plainPassword;
    }
}
