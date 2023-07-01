package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Nonnull
    List<Booking> findAllByCourtId(final long courtId);

    @Nonnull
    List<Booking> findAllByCourtAndTimeslotIndexAndDate(@Nonnull final Court court, final int timeslotIndex, @Nonnull final LocalDate date);
}
