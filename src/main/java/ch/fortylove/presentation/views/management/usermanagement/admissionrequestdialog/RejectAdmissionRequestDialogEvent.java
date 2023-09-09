package ch.fortylove.presentation.views.management.usermanagement.admissionrequestdialog;

import ch.fortylove.persistence.entity.User;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class RejectAdmissionRequestDialogEvent extends ComponentEvent<AdmissionRequestDialog> {

    @Nonnull private final User user;
    @Nonnull private final String message;

    protected RejectAdmissionRequestDialogEvent(@Nonnull final AdmissionRequestDialog source,
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

