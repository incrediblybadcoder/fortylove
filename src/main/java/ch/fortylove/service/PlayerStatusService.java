package ch.fortylove.service;

import ch.fortylove.persistence.entity.PlayerStatus;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface PlayerStatusService {

    @Nonnull
    PlayerStatus create(@Nonnull final PlayerStatus playerStatus);

    @Nonnull
    Optional<PlayerStatus> findByName(@Nonnull final String name);

    @Nonnull
    PlayerStatus getDefaultNewUserPlayerStatus();
}
