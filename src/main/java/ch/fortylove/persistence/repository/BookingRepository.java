package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Nonnull
    List<Booking> findAllByCourtId(final long courtId);


    @Nonnull
    @Transactional
    void deleteAllById(final long id);
}
