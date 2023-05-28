package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Nonnull
    Collection<Booking> findAllByCourtId(final long courtId);
}
