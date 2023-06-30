package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    @Nullable
    Court findById(final long id);

    @Query("SELECT c FROM courts c LEFT JOIN FETCH c.bookings b WHERE b.date = (:date)")
    List<Court> findAllWithBookingsByDate(@Nonnull final LocalDate date);
}
