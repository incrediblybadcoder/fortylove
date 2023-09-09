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

    @Nonnull private static final String DEFAULT_PLAYER_STATUS_GUEST = PlayerStatusSetupData.GUEST;
    @Nonnull private static final String DEFAULT_PLAYER_STATUS_MEMBER = PlayerStatusSetupData.ACTIVE;
    @Nonnull private static final String DEFAULT_PLAYER_STATUS_ADMIN = PlayerStatusSetupData.ACTIVE;

    @Nonnull private final PlayerStatusRepository playerStatusRepository;

    public PlayerStatusService(@Nonnull final PlayerStatusRepository playerStatusRepository) {
        this.playerStatusRepository = playerStatusRepository;
    }

    @Nonnull
    public DatabaseResult<PlayerStatus> create(@Nonnull final PlayerStatus playerStatus) {
        if (playerStatusRepository.findById(playerStatus.getId()).isPresent()) {
            return new DatabaseResult<>("Status existiert bereits: " + playerStatus.getIdentifier());
        }

        final String playerStatusName = playerStatus.getName();
        if (findByName(playerStatusName).isPresent() && isProtectedName(playerStatusName)) {
            return new DatabaseResult<>("Geschützter Spielerstatus: " + playerStatusName);
        }

        return new DatabaseResult<>(playerStatusRepository.save(playerStatus));
    }

    @Nonnull
    public DatabaseResult<PlayerStatus> update(@Nonnull final PlayerStatus playerStatus) {
        final Optional<PlayerStatus> playerStatusToUpdate = playerStatusRepository.findById(playerStatus.getId());
        if (playerStatusToUpdate.isEmpty()) {
            return new DatabaseResult<>("Status existiert nicht: " + playerStatus.getIdentifier());
        }

        final String playerStatusToUpdateName = playerStatusToUpdate.get().getName();
        if (isProtectedName(playerStatusToUpdateName) && !playerStatusToUpdateName.equals(playerStatus.getName())) {
            return new DatabaseResult<>("Geschützter Spielerstatus: " + playerStatusToUpdateName);
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

        final PlayerStatus playerStatusToDelete = playerStatusToDeleteOptional.get();
        if (isProtectedName(playerStatusToDelete.getName())) {
            return new DatabaseResult<>("Geschützter Spielerstatus: " + playerStatusToDelete.getName());
        }

        final Optional<PlayerStatus> replacementPlayerStatusOptional = playerStatusRepository.findById(replacementPlayerStatusId);
        if (replacementPlayerStatusOptional.isEmpty()) {
            return new DatabaseResult<>("Status existiert nicht: " + replacementPlayerStatusId);
        }

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
    public List<PlayerStatus> findAll() {
        return playerStatusRepository.findAll();
    }

    @Nonnull
    public PlayerStatus getDefaultGuestPlayerStatus() {
        return getDefaultPlayerStatus(DEFAULT_PLAYER_STATUS_GUEST);
    }

    @Nonnull
    public PlayerStatus getDefaultMemberPlayerStatus() {
        return getDefaultPlayerStatus(DEFAULT_PLAYER_STATUS_MEMBER);
    }

    @Nonnull
    public PlayerStatus getDefaultAdminPlayerStatus() {
        return getDefaultPlayerStatus(DEFAULT_PLAYER_STATUS_ADMIN);
    }

    @Nonnull
    private PlayerStatus getDefaultPlayerStatus(@Nonnull final String name) {
        final Optional<PlayerStatus> playerStatus = this.findByName(name);
        if (playerStatus.isPresent()) {
            return playerStatus.get();
        }
        throw new RecordNotFoundException("PlayerStatus " + name + " not found");
    }

    public boolean isProtectedName(@Nonnull final String name) {
        return PlayerStatusSetupData.getProtectedNames().contains(name);
    }
}
