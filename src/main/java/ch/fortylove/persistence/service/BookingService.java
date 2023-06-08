package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Booking;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
public interface BookingService {

    @Nonnull
    Booking create(@Nonnull final Booking booking);

    @Nonnull
    List<Booking> findAllByCourtId(final long courtId);
}
