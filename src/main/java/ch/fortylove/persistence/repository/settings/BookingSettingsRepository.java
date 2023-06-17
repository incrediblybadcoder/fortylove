package ch.fortylove.persistence.repository.settings;

import ch.fortylove.persistence.entity.settings.BookingSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingSettingsRepository extends JpaRepository<BookingSettings, Long> {
}
