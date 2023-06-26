package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.Court;

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
    List<Court> findAll();

    @Nonnull
    List<Court> findAllByDate(@Nonnull final LocalDate date);
}
