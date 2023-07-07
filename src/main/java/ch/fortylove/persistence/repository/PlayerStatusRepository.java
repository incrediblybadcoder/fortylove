package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.PlayerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PlayerStatusRepository extends JpaRepository<PlayerStatus, Long> {
    @Nullable
    PlayerStatus findByName(@Nonnull final String name);

}
