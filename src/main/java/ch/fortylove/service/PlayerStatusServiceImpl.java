package ch.fortylove.service;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.PlayerStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlayerStatusServiceImpl implements PlayerStatusService {

    @Nonnull public static final String DEFAULT_PLAYER_STATUS_FOR_NEW_USER = "aktiv";

    @Nonnull private final PlayerStatusRepository playerStatusRepository;

    public PlayerStatusServiceImpl(@Nonnull final PlayerStatusRepository playerStatusRepository) {
        this.playerStatusRepository = playerStatusRepository;
    }

    @Nonnull
    @Override
    public PlayerStatus create(@Nonnull final PlayerStatus playerStatus) {
        if (playerStatusRepository.findById(playerStatus.getId()).isPresent()) {
            throw new DuplicateRecordException(playerStatus);
        }
        return playerStatusRepository.save(playerStatus);
    }

    @Nonnull
    @Override
    public Optional<PlayerStatus> findByName(@Nonnull final String name) {
        return Optional.ofNullable(playerStatusRepository.findByName(name));
    }

    @Nonnull
    @Override
    public PlayerStatus getDefaultNewUserPlayerStatus() {
        final Optional<PlayerStatus> playerStatus = this.findByName(DEFAULT_PLAYER_STATUS_FOR_NEW_USER);
        if (playerStatus.isPresent()) {
            return playerStatus.get();
        }
        throw new RecordNotFoundException("PlayerStatus " + DEFAULT_PLAYER_STATUS_FOR_NEW_USER + " not found");
    }

    @Nonnull
    @Override
    public List<PlayerStatus> findAll() {
        return playerStatusRepository.findAll();
    }
}
