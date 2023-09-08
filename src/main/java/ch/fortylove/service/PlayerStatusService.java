package ch.fortylove.service;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.PlayerStatusType;
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

    @Nonnull private final PlayerStatusRepository playerStatusRepository;

    public PlayerStatusService(@Nonnull final PlayerStatusRepository playerStatusRepository) {
        this.playerStatusRepository = playerStatusRepository;
    }

    @Nonnull
    public DatabaseResult<PlayerStatus> create(@Nonnull final PlayerStatus playerStatus) {
        if (playerStatusRepository.findById(playerStatus.getId()).isPresent()) {
            return new DatabaseResult<>("Status existiert bereits: " + playerStatus.getIdentifier());
        }

        removeOtherDefaults(playerStatus);

        return new DatabaseResult<>(playerStatusRepository.save(playerStatus));
    }

    @Nonnull
    public DatabaseResult<PlayerStatus> update(@Nonnull final PlayerStatus playerStatus) {
        final Optional<PlayerStatus> playerStatusToUpdate = playerStatusRepository.findById(playerStatus.getId());
        if (playerStatusToUpdate.isEmpty()) {
            return new DatabaseResult<>("Status existiert nicht: " + playerStatus.getIdentifier());
        }

        if (playerStatusToUpdate.get().isDefault() && !playerStatus.isDefault()) {
            return new DatabaseResult<>(String.format("Es muss immer ein standard Status pro Typ (%s) geben.", playerStatus.getPlayerStatusType().getIdentifier()));
        } else if (!playerStatusToUpdate.get().isDefault() && playerStatus.isDefault()) {
            removeOtherDefaults(playerStatus);
        }

        return new DatabaseResult<>(playerStatusRepository.save(playerStatus));
    }

    private void removeOtherDefaults(final @Nonnull PlayerStatus playerStatus) {
        final List<PlayerStatus> allDefaultPlayerStatusOfType = playerStatusRepository.findAllByPlayerStatusTypeAndIsDefaultIsTrue(playerStatus.getPlayerStatusType());

        // there can only be one default playerStatus per playerStatusType
        if (allDefaultPlayerStatusOfType.size() > 1) {
            throw new IllegalStateException(String.format("There can't be more than one default playerStatus (%s) per playerStatusType (%s)", playerStatus, playerStatus.getPlayerStatusType()));
        }

        if (allDefaultPlayerStatusOfType.size() < 1 && !playerStatus.isDefault()) {
            playerStatus.setDefault(true);
        } else if (allDefaultPlayerStatusOfType.size() == 1 && playerStatus.isDefault()) {
            allDefaultPlayerStatusOfType.forEach(currentDefaultPlayerStatus -> currentDefaultPlayerStatus.setDefault(false));
        }
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

        if (playerStatusToDelete.isDefault()) {
            replacementPlayerStatus.setDefault(true);
        }

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
    public List<PlayerStatus> findAllByPlayerStatusType(@Nonnull final PlayerStatusType playerStatusType) {
        return playerStatusRepository.findAllByPlayerStatusType(playerStatusType);
    }

    @Nonnull
    public Optional<PlayerStatus> getDefaultPlayerStatusOfType(@Nonnull final PlayerStatusType playerStatusType) {
        final List<PlayerStatus> allDefaultPlayerStatusOfType = playerStatusRepository.findAllByPlayerStatusTypeAndIsDefaultIsTrue(playerStatusType);

        if (allDefaultPlayerStatusOfType.size() > 1) {
            throw new IllegalStateException(String.format("There can't be more than one default playerStatus per playerStatusType (%s)", playerStatusType));
        }

        return allDefaultPlayerStatusOfType.size() == 1 ?
                Optional.of(allDefaultPlayerStatusOfType.get(0)) :
                Optional.empty();
    }

    @Nonnull
    public PlayerStatus getDefaultNewUserPlayerStatus() {
        return getDefaultPlayerStatusOfType(PlayerStatusType.GUEST).orElseThrow(() -> new IllegalStateException(String.format("No default playerStatus of playerStatusType (%s)", PlayerStatusType.GUEST)));
    }

    @Nonnull
    public PlayerStatus getDefaultAdminPlayerStatus() {
        return getDefaultPlayerStatusOfType(PlayerStatusType.MEMBER).orElseThrow(() -> new IllegalStateException(String.format("No default playerStatus of playerStatus per playerStatusType (%s)", PlayerStatusType.MEMBER)));
    }
}
