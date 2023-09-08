package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.service.PlayerStatusService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringComponent
public class PlayerStatusTestDataFactory {

    public static final String DEFAULT_PLAYER_STATUS = "default_playerstatus";
    public static final int DEFAULT_BOOKINGS_PER_DAY = 1;
    public static final int DEFAULT_BOOKABLE_DAYS_IN_ADVANCE = 1;

    @Nonnull private final PlayerStatusService playerStatusService;

    @Autowired
    public PlayerStatusTestDataFactory(@Nonnull final PlayerStatusService playerStatusService) {
        this.playerStatusService = playerStatusService;
    }

    @Nonnull
    public PlayerStatus createPlayerStatus(@Nonnull final String name,
                                           final int bookingsPerDay,
                                           final int bookableDaysInAdvance) {
        return playerStatusService.create(new PlayerStatus(name, bookingsPerDay, bookableDaysInAdvance)).getData().get();
    }

    @Nonnull
    public PlayerStatus getDefault() {
        final Optional<PlayerStatus> defaultPlayerStatus = playerStatusService.findByName(DEFAULT_PLAYER_STATUS);
        if (defaultPlayerStatus.isEmpty()) {
            return createPlayerStatus(DEFAULT_PLAYER_STATUS, DEFAULT_BOOKINGS_PER_DAY, DEFAULT_BOOKABLE_DAYS_IN_ADVANCE);
        }

        return defaultPlayerStatus.get();
    }
}
