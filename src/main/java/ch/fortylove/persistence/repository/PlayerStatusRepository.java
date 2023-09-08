package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.PlayerStatus;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerStatusRepository extends JpaRepository<PlayerStatus, UUID> {

    @Nullable
    PlayerStatus findByName(@Nonnull final String name);
}
