package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.CourtDTO;
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
    public List<CourtDTO> findAllByDate(@Nonnull final LocalDate date) {
        final List<CourtDTO> courts = new ArrayList<>();

        final List<Court> allCourts = findAll();
        allCourts.forEach(court -> courts.add(new CourtDTO(court.getId(), getBookingsByDate(court, date))));

        return courts;
    }

    @Nonnull
    private List<Booking> getBookingsByDate(@Nonnull final Court court,
                                            @Nonnull final LocalDate date) {
        final List<Booking> allBookings = court.getBookings();
        return allBookings.stream().filter(booking -> booking.getDate().equals(date)).toList();
    }
}
