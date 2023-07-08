package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Nonnull
    List<Booking> findAllByCourtId(final long courtId);

    @Nonnull
    List<Booking> findAllByCourtAndTimeslotAndDate(@Nonnull final Court court, @Nonnull final Timeslot timeslot, @Nonnull final LocalDate date);

    @Query("SELECT COUNT(b) FROM bookings b WHERE b.owner = :user AND b.date = :date")
    int getUserBookingsOnDay(@Nonnull final User user, @Nonnull final  LocalDate date);
}
