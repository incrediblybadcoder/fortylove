package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.BookingSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingSettingsRepository extends JpaRepository<BookingSettingsEntity, Long> {
}
