package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.CourtDTO;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourtService {

    @Nonnull
    CourtDTO create(@Nonnull final CourtDTO court);

    @Nonnull
    Optional<CourtDTO> findById(final long id);

    @Nonnull
    List<CourtDTO> findAll();

    @Nonnull
    List<CourtDTO> findAllByDate(@Nonnull final LocalDate date);
}
