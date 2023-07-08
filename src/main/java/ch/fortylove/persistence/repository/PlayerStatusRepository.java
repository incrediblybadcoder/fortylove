package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.PlayerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Repository
public interface PlayerStatusRepository extends JpaRepository<PlayerStatus, Long> {

    @Nullable
    PlayerStatus findByName(@Nonnull final String name);
}
