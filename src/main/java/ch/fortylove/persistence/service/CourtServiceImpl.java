package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
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
    public CourtDTO create(@Nonnull final CourtDTO court) {
        final CourtEntity courtEntity = courtRepository.save(courtMapper.convert(court, new CycleAvoidingMappingContext()));
        return courtMapper.convert(courtEntity, new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public Optional<CourtDTO> findById(final long id) {
        return Optional.ofNullable(courtMapper.convert(courtRepository.findById(id), new CycleAvoidingMappingContext()));
    }

    @Nonnull
    @Override
    public List<CourtDTO> findAll() {
        return courtMapper.convert(courtRepository.findAll(), new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public List<CourtDTO> findAllByDate(@Nonnull final LocalDate date) {
        final List<CourtDTO> courts = new ArrayList<>();

        final List<CourtDTO> allCourts = findAll();
        allCourts.forEach(court -> courts.add(new CourtDTO(court.getId(), getBookingsByDate(court, date))));

        return courts;
    }

    @Nonnull
    private List<BookingDTO> getBookingsByDate(@Nonnull final CourtDTO court,
                                               @Nonnull final LocalDate date) {
        final List<BookingDTO> allBookings = court.getBookings();
        return allBookings.stream().filter(booking -> booking.getDate().equals(date)).toList();
    }
}
