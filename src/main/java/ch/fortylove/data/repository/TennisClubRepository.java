package ch.fortylove.data.repository;

import ch.fortylove.data.entity.TennisClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TennisClubRepository extends JpaRepository<TennisClub, Long> {
}
