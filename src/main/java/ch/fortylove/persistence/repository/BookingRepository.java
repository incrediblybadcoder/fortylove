package ch.fortylove.persistence.repository;

import ch.fortylove.persistence.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Nonnull
    List<BookingEntity> findAllByCourtId(final long courtId);
}
