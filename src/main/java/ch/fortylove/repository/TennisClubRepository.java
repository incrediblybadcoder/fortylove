package ch.fortylove.repository;

import ch.fortylove.model.TennisClub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TennisClubRepository extends JpaRepository<TennisClub, Long> {
}
