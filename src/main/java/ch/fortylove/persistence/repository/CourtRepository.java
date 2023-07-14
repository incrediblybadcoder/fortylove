package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourtRepository extends JpaRepository<Court, UUID> {
}
