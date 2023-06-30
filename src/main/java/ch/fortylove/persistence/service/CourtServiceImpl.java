package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourtServiceImpl implements CourtService {

    @Nonnull private final CourtRepository courtRepository;

    @Autowired
    public CourtServiceImpl(@Nonnull final CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    @Nonnull
    @Override
    public Court create(@Nonnull final Court court) {
        return courtRepository.save(court);
    }

    @Nonnull
    @Override
    public Optional<Court> findById(final long id) {
        return Optional.ofNullable(courtRepository.findById(id));
    }

    @Nonnull
    @Override
    public List<Court> findAll() {
        return courtRepository.findAll();
    }

    @Nonnull
    @Override
    public List<Court> findAllWithBookingsByDate(@Nonnull final LocalDate date) {
        return courtRepository.findAllWithBookingsByDate(date);
    }
}
