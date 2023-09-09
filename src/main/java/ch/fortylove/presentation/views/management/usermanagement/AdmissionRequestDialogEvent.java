package ch.fortylove.presentation.views.management.usermanagement;

import ch.fortylove.persistence.entity.User;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class AdmissionRequestDialogEvent extends ComponentEvent<AdmissionRequestDialog> {

    @Nonnull private final Type type;
    @Nonnull private final User user;
    @Nonnull private final String message;

    public enum Type {
        ACCEPT,
        REJECT
    }

    protected AdmissionRequestDialogEvent(@Nonnull final AdmissionRequestDialog source,
                                          @Nonnull final Type type,
                                          @Nonnull final User user,
                                          @Nonnull final String message) {
        super(source, false);
        this.type = type;
        this.user = user;
        this.message = message;
    }

    @Nonnull
    public static AdmissionRequestDialogEvent acceptAdmission(@Nonnull final AdmissionRequestDialog source,
                                                              @Nonnull final User user,
                                                              @Nonnull final String message) {
        return new AdmissionRequestDialogEvent(source, Type.ACCEPT, user, message);
    }

    @Nonnull
    public static AdmissionRequestDialogEvent rejectAdmission(@Nonnull final AdmissionRequestDialog source,
                                                              @Nonnull final User user,
                                                              @Nonnull final String message) {
        return new AdmissionRequestDialogEvent(source, Type.REJECT, user, message);
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    @Nonnull
    public String getMessage() {
        return message;
    }

    @Nonnull
    public Type getType() {
        return type;
    }
}

