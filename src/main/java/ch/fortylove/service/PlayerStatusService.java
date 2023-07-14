package ch.fortylove.service;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.PlayerStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
@Transactional
public class PlayerStatusService {

    @Nonnull public static final String DEFAULT_PLAYER_STATUS_FOR_NEW_USER = "aktiv";

    @Nonnull private final PlayerStatusRepository playerStatusRepository;

    public PlayerStatusService(@Nonnull final PlayerStatusRepository playerStatusRepository) {
        this.playerStatusRepository = playerStatusRepository;
    }

    @Nonnull
    public PlayerStatus create(@Nonnull final PlayerStatus playerStatus) {
        if (playerStatusRepository.findById(playerStatus.getId()).isPresent()) {
            throw new DuplicateRecordException(playerStatus);
        }
        return playerStatusRepository.save(playerStatus);
    }

    @Nonnull
    public Optional<PlayerStatus> findByName(@Nonnull final String name) {
        return Optional.ofNullable(playerStatusRepository.findByName(name));
    }

    @Nonnull
    public PlayerStatus getDefaultNewUserPlayerStatus() {
        final Optional<PlayerStatus> playerStatus = this.findByName(DEFAULT_PLAYER_STATUS_FOR_NEW_USER);
        if (playerStatus.isPresent()) {
            return playerStatus.get();
        }
        throw new RecordNotFoundException("PlayerStatus " + DEFAULT_PLAYER_STATUS_FOR_NEW_USER + " not found");
    }
}
