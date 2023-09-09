package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Court;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourtRepository extends JpaRepository<Court, UUID> {

    @Nonnull
    List<Court> findByOrderByNumberAsc();
}
