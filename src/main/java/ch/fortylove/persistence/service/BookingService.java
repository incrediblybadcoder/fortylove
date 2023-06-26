package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.BookingDTO;

import javax.annotation.Nonnull;
import java.util.List;

public interface BookingService {

    @Nonnull
    BookingDTO create(@Nonnull final BookingDTO booking);

    @Nonnull
    List<BookingDTO> findAllByCourtId(final long courtId);

    @Nonnull
    List<BookingDTO> findAll();
}
