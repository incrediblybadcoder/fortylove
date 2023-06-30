package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Booking;

import javax.annotation.Nonnull;
import java.util.List;

public interface BookingService {

    @Nonnull
    Booking create(@Nonnull final Booking booking);

    @Nonnull
    List<Booking> findAllByCourtId(final long id);

    @Nonnull
    List<Booking> findAll();
}
