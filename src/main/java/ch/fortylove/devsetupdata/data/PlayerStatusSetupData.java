package ch.fortylove.devsetupdata.data;

import ch.fortylove.devsetupdata.DevSetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.service.PlayerStatusService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Optional;

@DevSetupData
public class PlayerStatusSetupData {

    @Nonnull private final PlayerStatusService playerStatusService;


    @Autowired
    public PlayerStatusSetupData(@Nonnull final PlayerStatusService playerStatusService) {
        this.playerStatusService = playerStatusService;
    }

    public void createPlayerStatus() {
        createPlayerStatusIfNotFound("aktiv", 2, 7);
        createPlayerStatusIfNotFound("passiv", 1, 3);
        createPlayerStatusIfNotFound("turnier spieler", 3, 7);
        createPlayerStatusIfNotFound("inaktiv", 0, 0);
    }

    private void createPlayerStatusIfNotFound(final String name, final int bookingsPerDay, final int bookableDaysInAdvance) {
       final Optional<PlayerStatus> playerStatus = playerStatusService.findByName(name);
       if(playerStatus.isEmpty()) {
           playerStatusService.create(new PlayerStatus(0L, name, null, bookingsPerDay, bookableDaysInAdvance));
       }

    }
}
