package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.BookingSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingSettingsRepository extends JpaRepository<BookingSettings, UUID> {
}
