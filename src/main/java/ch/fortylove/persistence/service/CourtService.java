package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.entity.Court;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface CourtService {

    @Nonnull
    Court create(@Nonnull final Court court);

    @Nonnull
    Optional<Court> findById(final long id);

    @Nonnull
    List<Court> findAll();

    @Nonnull
    List<CourtDTO> findAllByDate(@Nonnull final LocalDate date);
}
