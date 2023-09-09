package ch.fortylove.service;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.repository.CourtRepository;
import ch.fortylove.service.util.DatabaseResult;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CourtService {

    @Nonnull private final EntityManager entityManager;
    @Nonnull private final CourtRepository courtRepository;
    @Nonnull private final BookingService bookingService;

    @Autowired
    public CourtService(@Nonnull final EntityManager entityManager,
                        @Nonnull final CourtRepository courtRepository,
                        @Nonnull final BookingService bookingService) {
        this.entityManager = entityManager;
        this.courtRepository = courtRepository;
        this.bookingService = bookingService;
    }

    @Nonnull
    public DatabaseResult<Court> create(@Nonnull final Court court) {
        if (courtRepository.findById(court.getId()).isPresent()) {
            return new DatabaseResult<>("Court existiert bereits: " + court.getIdentifier());
        }
        return new DatabaseResult<>(courtRepository.save(court));
    }

    @Nonnull
    public DatabaseResult<Court> update(@Nonnull final Court court) {
        if (courtRepository.findById(court.getId()).isEmpty()) {
            return new DatabaseResult<>("Court existiert nicht: " + court.getIdentifier());
        }
        return new DatabaseResult<>(courtRepository.save(court));
    }

    @Nonnull
    public DatabaseResult<UUID> delete(@Nonnull final UUID id) {
        final Optional<Court> courtOptional = courtRepository.findById(id);
        if (courtOptional.isEmpty()) {
            return new DatabaseResult<>("Court existiert nicht: " + id);
        }

        final Court court = courtOptional.get();
        court.getBookings().forEach(booking -> bookingService.delete(booking.getId()));
        court.getBookings().clear();
        courtRepository.delete(court);

        return new DatabaseResult<>(id);
    }

    @Nonnull
    public Optional<Court> findById(@Nonnull final UUID id) {
        return courtRepository.findById(id);
    }

    @Nonnull
    public List<Court> findAllWithBookingsByDate(@Nonnull final LocalDate date) {
        final Session session = entityManager.unwrap(Session.class);
        final Filter filter = session.enableFilter("bookingDateFilter");
        filter.setParameter("date", date);
        final List<Court> courts = findAll();
        session.disableFilter("bookingDateFilter");

        return courts;
    }

    @Nonnull
    public List<Court> findAll() {
        return courtRepository.findByOrderByNumberAsc();
    }
}
