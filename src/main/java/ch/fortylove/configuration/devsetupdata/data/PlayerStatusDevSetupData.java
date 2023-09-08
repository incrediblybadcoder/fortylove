package ch.fortylove.configuration.devsetupdata.data;

import ch.fortylove.configuration.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.PlayerStatusType;
import ch.fortylove.service.PlayerStatusService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@DevSetupData
public class PlayerStatusDevSetupData {

    @Nonnull public static final String PASSIV = "Passiv";
    @Nonnull public static final String TURNIER = "Turnier";
    @Nonnull public static final String INAKTIV = "Inaktiv";

    @Nonnull private final PlayerStatusService playerStatusService;

    @Autowired
    public PlayerStatusDevSetupData(@Nonnull final PlayerStatusService playerStatusService) {
        this.playerStatusService = playerStatusService;
    }

    public void createPlayerStatus() {
        createPlayerStatusIfNotFound(PASSIV, PlayerStatusType.MEMBER, false, 1, 3);
        createPlayerStatusIfNotFound(TURNIER, PlayerStatusType.MEMBER, false, 3, 7);
        createPlayerStatusIfNotFound(INAKTIV, PlayerStatusType.MEMBER, false, 0, 0);
    }

    private void createPlayerStatusIfNotFound(@Nonnull final String name,
                                              @Nonnull final PlayerStatusType playerStatusType,
                                              final boolean isDefault,
                                              final int bookingsPerDay,
                                              final int bookableDaysInAdvance) {
        final Optional<PlayerStatus> playerStatus = playerStatusService.findByName(name);
        if (playerStatus.isEmpty()) {
            playerStatusService.create(new PlayerStatus(name, playerStatusType, isDefault, bookingsPerDay, bookableDaysInAdvance));
        }
    }
}
