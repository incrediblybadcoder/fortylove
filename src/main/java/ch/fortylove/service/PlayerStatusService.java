package ch.fortylove.service;

import ch.fortylove.configuration.setupdata.data.PlayerStatusSetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.PlayerStatusRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PlayerStatusService {

    @Nonnull public static final String DEFAULT_PLAYER_STATUS_FOR_NEW_USER = PlayerStatusSetupData.AKTIV;
    @Nonnull public static final String DEFAULT_PLAYER_STATUS_FOR_ADMIN = PlayerStatusSetupData.AKTIV;

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
    public PlayerStatus update(@Nonnull final PlayerStatus playerStatus) {
        if (playerStatusRepository.findById(playerStatus.getId()).isEmpty()) {
            throw new RecordNotFoundException(playerStatus);
        }
        return playerStatusRepository.save(playerStatus);
    }

    public void delete(@Nonnull final UUID playerStatusToDeleteId,
                       @Nonnull final UUID replacementPlayerStatusId) {
        final PlayerStatus playerStatusToDelete = playerStatusRepository.findById(playerStatusToDeleteId)
                .orElseThrow(() -> new RecordNotFoundException(playerStatusToDeleteId));
        final PlayerStatus replacementPlayerStatus = playerStatusRepository.findById(replacementPlayerStatusId)
                .orElseThrow(() -> new RecordNotFoundException(replacementPlayerStatusId));

        playerStatusToDelete.getUsers().forEach(user -> {
            user.setPlayerStatus(replacementPlayerStatus);
            replacementPlayerStatus.getUsers().add(user);
        });

        playerStatusToDelete.getUsers().clear();
        playerStatusRepository.delete(playerStatusToDelete);
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

    @Nonnull
    public List<PlayerStatus> findAll() {
        return playerStatusRepository.findAll();
    }

    @Nonnull
    public PlayerStatus getDefaultAdminPlayerStatus() {
        final Optional<PlayerStatus> playerStatus = this.findByName(DEFAULT_PLAYER_STATUS_FOR_ADMIN);
        if (playerStatus.isPresent()) {
            return playerStatus.get();
        }
        throw new RecordNotFoundException("PlayerStatus " + DEFAULT_PLAYER_STATUS_FOR_ADMIN + " not found");
    }
}
