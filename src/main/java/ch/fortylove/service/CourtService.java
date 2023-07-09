package ch.fortylove.service;

import ch.fortylove.persistence.entity.Court;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourtService {

    @Nonnull
    Court create(@Nonnull final Court court);

    @Nonnull
    Optional<Court> findById(final long id);

    @Nonnull
    List<Court> findAllWithBookingsByDate(@Nonnull final LocalDate date);
}
