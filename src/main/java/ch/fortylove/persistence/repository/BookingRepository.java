package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Nonnull
    List<Booking> findAllByCourtId(@Nonnull final UUID id);

    @Nonnull
    List<Booking> findAllByCourtAndTimeslotAndDate(@Nonnull final Court court, @Nonnull final Timeslot timeslot, @Nonnull final LocalDate date);

    @Nonnull
    List<Booking> findAllByTimeslotAndDate(@Nonnull final Timeslot timeslot, @Nonnull final LocalDate date);

    @Query("SELECT COUNT(b) FROM bookings b WHERE b.owner = :user AND b.date = :date")
    int getUserBookingsOnDay(@Nonnull final User user, @Nonnull final  LocalDate date);
}
