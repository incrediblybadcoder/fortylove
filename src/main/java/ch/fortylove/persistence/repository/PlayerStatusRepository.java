package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.PlayerStatusType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerStatusRepository extends JpaRepository<PlayerStatus, UUID> {

    @Nullable
    PlayerStatus findByName(@Nonnull final String name);

    @Nonnull
    List<PlayerStatus> findAllByPlayerStatusTypeAndIsDefaultIsTrue(@Nonnull final PlayerStatusType playerStatusType);

    @Nonnull
    List<PlayerStatus> findAllByPlayerStatusType(@Nonnull final PlayerStatusType playerStatusType);
}
