package ch.fortylove.service;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.repository.CourtRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CourtService {

    @Nonnull private final EntityManager entityManager;
    @Nonnull private final CourtRepository courtRepository;

    @Autowired
    public CourtService(@Nonnull final EntityManager entityManager,
                        @Nonnull final CourtRepository courtRepository) {
        this.entityManager = entityManager;
        this.courtRepository = courtRepository;
    }

    @Nonnull
    public Court create(@Nonnull final Court court) {
        if (courtRepository.findById(court.getId()).isPresent()) {
            throw new DuplicateRecordException(court);
        }
        return courtRepository.save(court);
    }

    @Nonnull
    public Optional<Court> findById(@Nonnull final UUID id) {
        return courtRepository.findById(id);
    }

    @Nonnull
    public Optional<Court> findByNumber(@Nonnull final UUID id) {
        return courtRepository.findById(id);
    }

    @Nonnull
    public List<Court> findAllWithBookingsByDate(@Nonnull final LocalDate date) {
        final Session session = entityManager.unwrap(Session.class);
        final Filter filter = session.enableFilter("bookingDateFilter");
        filter.setParameter("date", date);
        final List<Court> courts = courtRepository.findAll();
        session.disableFilter("bookingDateFilter");

        return courts;
    }

    @Nonnull
    public List<Court> findAll() {
        return courtRepository.findAll();
    }
}
