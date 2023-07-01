package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Booking;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    @Nonnull
    Booking create(@Nonnull final Booking booking);

    @Nonnull
    Booking update(@Nonnull final Booking booking);

    @Nonnull
    Optional<Booking> findById(final long id);

    @Nonnull
    List<Booking> findAllByCourtId(final long id);

    @Transactional
    void delete(@Nonnull final Booking booking);
}
