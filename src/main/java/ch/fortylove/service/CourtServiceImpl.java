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

@Service
@Transactional
public class CourtServiceImpl implements CourtService {

    @Nonnull private final EntityManager entityManager;
    @Nonnull private final CourtRepository courtRepository;

    @Autowired
    public CourtServiceImpl(@Nonnull final EntityManager entityManager,
                            @Nonnull final CourtRepository courtRepository) {
        this.entityManager = entityManager;
        this.courtRepository = courtRepository;
    }

    @Nonnull
    @Override
    public Court create(@Nonnull final Court court) {
        if (courtRepository.findById(court.getId()).isPresent()) {
            throw new DuplicateRecordException(court);
        }
        return courtRepository.save(court);
    }

    @Nonnull
    @Override
    public Optional<Court> findById(final long id) {
        return courtRepository.findById(id);
    }

    @Nonnull
    @Override
    public List<Court> findAllWithBookingsByDate(@Nonnull final LocalDate date) {
        final Session session = entityManager.unwrap(Session.class);
        final Filter filter = session.enableFilter("bookingDateFilter");
        filter.setParameter("date", date);
        final List<Court> courts = courtRepository.findAll();
        session.disableFilter("bookingDateFilter");

        return courts;
    }
}
