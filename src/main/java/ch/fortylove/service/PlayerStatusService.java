package ch.fortylove.service;

import ch.fortylove.configuration.setupdata.data.PlayerStatusSetupData;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.PlayerStatusRepository;
import ch.fortylove.service.util.DatabaseResult;
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
    public DatabaseResult<PlayerStatus> create(@Nonnull final PlayerStatus playerStatus) {
        if (playerStatusRepository.findById(playerStatus.getId()).isPresent()) {
            return new DatabaseResult<>("Status existiert bereits: " + playerStatus.getIdentifier());
        }
        return new DatabaseResult<>(playerStatusRepository.save(playerStatus));
    }

    @Nonnull
    public DatabaseResult<PlayerStatus> update(@Nonnull final PlayerStatus playerStatus) {
        if (playerStatusRepository.findById(playerStatus.getId()).isEmpty()) {
            return new DatabaseResult<>("Status existiert nicht: " + playerStatus.getIdentifier());
        }
        return new DatabaseResult<>(playerStatusRepository.save(playerStatus));
    }

    @Nonnull
    public DatabaseResult<UUID> delete(@Nonnull final UUID playerStatusToDeleteId,
                                       @Nonnull final UUID replacementPlayerStatusId) {
        final Optional<PlayerStatus> playerStatusToDeleteOptional = playerStatusRepository.findById(playerStatusToDeleteId);
        if (playerStatusToDeleteOptional.isEmpty()) {
            return new DatabaseResult<>("Status existiert nicht: " + playerStatusToDeleteId);
        }

        final Optional<PlayerStatus> replacementPlayerStatusOptional = playerStatusRepository.findById(replacementPlayerStatusId);
        if (replacementPlayerStatusOptional.isEmpty()) {
            return new DatabaseResult<>("Status existiert nicht: " + replacementPlayerStatusId);
        }

        final PlayerStatus playerStatusToDelete = playerStatusToDeleteOptional.get();

        if (playerStatusRepository.findAll().size() == 1) {
            return new DatabaseResult<>("Letzter Status kann nicht gelöscht werden: " + playerStatusToDelete);
        }

        final PlayerStatus replacementPlayerStatus = replacementPlayerStatusOptional.get();

        playerStatusToDelete.getUsers().forEach(user -> {
            user.setPlayerStatus(replacementPlayerStatus);
            replacementPlayerStatus.getUsers().add(user);
        });

        playerStatusToDelete.getUsers().clear();
        playerStatusRepository.delete(playerStatusToDelete);

        return new DatabaseResult<>(playerStatusToDeleteId);
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
