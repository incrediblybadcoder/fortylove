package ch.fortylove.configuration.setupdata.data;

import ch.fortylove.configuration.setupdata.SetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.service.PlayerStatusService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@SetupData
public class PlayerStatusSetupData implements ch.fortylove.configuration.setupdata.data.SetupData {

    @Nonnull public static final String ACTIVE = "Aktiv";
    @Nonnull public static final String PASSIVE = "Passiv";
    @Nonnull public static final String INACTIVE = "Inaktiv";
    @Nonnull public static final String TOURNAMENT = "Turnier";
    @Nonnull public static final String GUEST = "Gast";

    @Nonnull private final PlayerStatusService playerStatusService;

    @Autowired
    public PlayerStatusSetupData(@Nonnull final PlayerStatusService playerStatusService) {
        this.playerStatusService = playerStatusService;
    }

    @Override
    public void createData() {
        createPlayerStatusIfNotFound(ACTIVE, 2, 7);
        createPlayerStatusIfNotFound(PASSIVE, 1, 3);
        createPlayerStatusIfNotFound(INACTIVE, 0, 0);
        createPlayerStatusIfNotFound(TOURNAMENT, 3, 7);
        createPlayerStatusIfNotFound(GUEST, 2, 7);
    }

    @Nonnull
    public static List<String> getProtectedNames() {
        return List.of(ACTIVE, PASSIVE, INACTIVE, TOURNAMENT, GUEST);
    }

    @Transactional
    private void createPlayerStatusIfNotFound(@Nonnull final String name,
                                              final int bookingsPerDay,
                                              final int bookableDaysInAdvance) {
        final Optional<PlayerStatus> playerStatus = playerStatusService.findByName(name);
        if (playerStatus.isEmpty()) {
            playerStatusService.create(new PlayerStatus(name, bookingsPerDay, bookableDaysInAdvance));
        }
    }
}
