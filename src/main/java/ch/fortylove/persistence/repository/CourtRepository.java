package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.CourtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public interface CourtRepository extends JpaRepository<CourtEntity, Long> {

    @Nullable
    CourtEntity findById(final long id);
}
