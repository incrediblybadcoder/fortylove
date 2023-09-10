package ch.fortylove.presentation.views.management.usermanagement.admissiondialog.events;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.views.management.usermanagement.admissiondialog.AdmissionDialog;
import com.vaadin.flow.component.ComponentEvent;
import jakarta.annotation.Nonnull;

public class AcceptAdmissionEvent extends ComponentEvent<AdmissionDialog> {

    @Nonnull private final User user;
    @Nonnull private final PlayerStatus playerStatus;
    @Nonnull private final String message;

    public AcceptAdmissionEvent(@Nonnull final AdmissionDialog source,
                                   @Nonnull final User user,
                                   @Nonnull final PlayerStatus playerStatus,
                                   @Nonnull final String message) {
        super(source, false);
        this.user = user;
        this.playerStatus = playerStatus;
        this.message = message;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    @Nonnull
    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    @Nonnull
    public String getMessage() {
        return message;
    }
}

