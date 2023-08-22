package ch.fortylove.presentation.views.management.playerstatusmanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.presentation.components.managementform.ManagementForm;
import ch.fortylove.presentation.components.managementform.events.ManagementFormDeleteEvent;
import jakarta.annotation.Nonnull;

public class PlayerStatusFormDeleteEvent extends ManagementFormDeleteEvent<PlayerStatus> {

    @Nonnull private final PlayerStatus replacementPlayerStatus;

    public PlayerStatusFormDeleteEvent(@Nonnull final ManagementForm<PlayerStatus> source,
                                       @Nonnull final PlayerStatus playerStatus,
                                       @Nonnull final PlayerStatus replacementPlayerStatus) {
        super(source, playerStatus);
        this.replacementPlayerStatus = replacementPlayerStatus;
    }

    @Nonnull
    public PlayerStatus getReplacementPlayerStatus() {
        return replacementPlayerStatus;
    }
}
