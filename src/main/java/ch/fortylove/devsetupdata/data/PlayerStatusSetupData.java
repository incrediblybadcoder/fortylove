package ch.fortylove.devsetupdata.data;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.service.PlayerStatusService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Optional;

@DevSetupData
public class PlayerStatusSetupData {

    @Nonnull public static final String AKTIV = "aktiv";
    @Nonnull public static final String PASSIV = "passiv";
    @Nonnull public static final String TURNIER = "turnier";
    @Nonnull public static final String INAKTIV = "inaktiv";

    @Nonnull private final PlayerStatusService playerStatusService;

    @Autowired
    public PlayerStatusSetupData(@Nonnull final PlayerStatusService playerStatusService) {
        this.playerStatusService = playerStatusService;
    }

    public void createPlayerStatus() {
        createPlayerStatusIfNotFound(AKTIV, 2, 7);
        createPlayerStatusIfNotFound(PASSIV, 1, 3);
        createPlayerStatusIfNotFound(TURNIER, 3, 7);
        createPlayerStatusIfNotFound(INAKTIV, 0, 0);
    }

    @Transactional
    private void createPlayerStatusIfNotFound(@Nonnull final String name,
                                              final int bookingsPerDay,
                                              final int bookableDaysInAdvance) {
       final Optional<PlayerStatus> playerStatus = playerStatusService.findByName(name);
       if(playerStatus.isEmpty()) {
           playerStatusService.create(new PlayerStatus(name, bookingsPerDay, bookableDaysInAdvance));
       }
    }
}
