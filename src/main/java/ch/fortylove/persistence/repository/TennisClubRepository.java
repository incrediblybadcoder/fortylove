package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.TennisClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TennisClubRepository extends JpaRepository<TennisClub, Long> {
}
