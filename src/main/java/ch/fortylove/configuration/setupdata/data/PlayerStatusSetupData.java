package ch.fortylove.configuration.setupdata.data;

import ch.fortylove.configuration.setupdata.SetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.PlayerStatusType;
import ch.fortylove.service.PlayerStatusService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SetupData
public class PlayerStatusSetupData {

    @Nonnull public static final String ACTIVE = "Aktiv";
    @Nonnull public static final String GUEST = "Gast";

    @Nonnull private final PlayerStatusService playerStatusService;

    @Autowired
    public PlayerStatusSetupData(@Nonnull final PlayerStatusService playerStatusService) {
        this.playerStatusService = playerStatusService;
    }

    public void createPlayerStatus() {
        createPlayerStatusIfNotFound(ACTIVE, PlayerStatusType.MEMBER, true, 2, 7);
        createPlayerStatusIfNotFound(GUEST, PlayerStatusType.GUEST, true, 2, 7);
    }

    @Transactional
    private void createPlayerStatusIfNotFound(@Nonnull final String name,
                                              @Nonnull final PlayerStatusType playerStatusType,
                                              final boolean isDefault,
                                              final int bookingsPerDay,
                                              final int bookableDaysInAdvance) {
        final Optional<PlayerStatus> defaultPlayerStatusOfType = playerStatusService.getDefaultPlayerStatusOfType(playerStatusType);
        if (defaultPlayerStatusOfType.isEmpty()) {
            playerStatusService.create(new PlayerStatus(name, playerStatusType, isDefault, bookingsPerDay, bookableDaysInAdvance));
        }
    }
}
