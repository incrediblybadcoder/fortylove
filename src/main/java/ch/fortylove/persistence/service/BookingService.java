package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.Booking;

import javax.annotation.Nonnull;
import java.util.List;

public interface BookingService {

    @Nonnull
    Booking create(@Nonnull final Booking booking);

    @Nonnull
    List<Booking> findAllByCourtId(final long courtId);

    @Nonnull
    List<Booking> findAll();
}
