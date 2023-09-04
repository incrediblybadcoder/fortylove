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

    @Nonnull private final PlayerStatusService playerStatusService;

    @Autowired
    public PlayerStatusTestDataFactory(@Nonnull final PlayerStatusService playerStatusService) {
        this.playerStatusService = playerStatusService;
    }

    @Nonnull
    public PlayerStatus createPlayerStatus(@Nonnull final String name) {
        return playerStatusService.create(new PlayerStatus(name, 1, 1)).getData().get();
    }

    @Nonnull
    public PlayerStatus getDefault() {
        final Optional<PlayerStatus> defaultPlayerStatus = playerStatusService.findByName(DEFAULT_PLAYER_STATUS);
        if (defaultPlayerStatus.isEmpty()) {
            return createPlayerStatus(DEFAULT_PLAYER_STATUS);
        }

        return defaultPlayerStatus.get();
    }
}
