package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.Booking;
import ch.fortylove.persistence.dto.Court;
import ch.fortylove.persistence.dto.mapper.CourtMapper;
import ch.fortylove.persistence.dto.mapper.CycleAvoidingMappingContext;
import ch.fortylove.persistence.entity.CourtEntity;
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
    @Nonnull private final CourtMapper courtMapper;

    @Autowired
    public CourtServiceImpl(@Nonnull final CourtRepository courtRepository,
                            @Nonnull final CourtMapper courtMapper) {
        this.courtRepository = courtRepository;
        this.courtMapper = courtMapper;
    }

    @Nonnull
    @Override
    public Court create(@Nonnull final Court court) {
        final CourtEntity courtEntity = courtRepository.save(courtMapper.convert(court, new CycleAvoidingMappingContext()));
        return courtMapper.convert(courtEntity, new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public Optional<Court> findById(final long id) {
        return Optional.ofNullable(courtMapper.convert(courtRepository.findById(id), new CycleAvoidingMappingContext()));
    }

    @Nonnull
    @Override
    public List<Court> findAll() {
        return courtMapper.convert(courtRepository.findAll(), new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public List<Court> findAllByDate(@Nonnull final LocalDate date) {
        final List<Court> courts = new ArrayList<>();

        final List<Court> allCourts = findAll();
        allCourts.forEach(court -> courts.add(new Court(court.getId(), getBookingsByDate(court, date))));

        return courts;
    }

    @Nonnull
    private List<Booking> getBookingsByDate(@Nonnull final Court court,
                                            @Nonnull final LocalDate date) {
        final List<Booking> allBookings = court.getBookings();
        return allBookings.stream().filter(booking -> booking.getDate().equals(date)).toList();
    }
}
