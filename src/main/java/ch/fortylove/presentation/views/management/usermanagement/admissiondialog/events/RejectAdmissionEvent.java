package ch.fortylove.presentation.views.management.usermanagement.admissiondialog.events;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.views.management.usermanagement.admissiondialog.AdmissionDialog;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class RejectAdmissionEvent extends ComponentEvent<AdmissionDialog> {

    @Nonnull private final User user;
    @Nonnull private final String message;

    public RejectAdmissionEvent(@Nonnull final AdmissionDialog source,
                                   @Nonnull final User user,
                                   @Nonnull final String message) {
        super(source, false);
        this.user = user;
        this.message = message;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    @Nonnull
    public String getMessage() {
        return message;
    }
}

