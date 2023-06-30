package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.mapper.CourtMapper;
import ch.fortylove.persistence.dto.mapper.CycleAvoidingMappingContext;
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
    @Nonnull private final CourtMapper courtMapper;

    @Autowired
    public CourtServiceImpl(@Nonnull final CourtRepository courtRepository,
                            @Nonnull final CourtMapper courtMapper) {
        this.courtRepository = courtRepository;
        this.courtMapper = courtMapper;
    }

    @Nonnull
    @Override
    public CourtDTO create(@Nonnull final CourtDTO courtDTO) {
        final Court court = courtRepository.save(courtMapper.convert(courtDTO, new CycleAvoidingMappingContext()));
        return courtMapper.convert(court, new CycleAvoidingMappingContext());
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
        final List<CourtDTO> courtDTOs = new ArrayList<>();

        final List<CourtDTO> allCourtDTOs = findAll();
        allCourtDTOs.forEach(court -> courtDTOs.add(new CourtDTO(court.getId(), getBookingsByDate(court, date))));

        return courtDTOs;
    }

    @Nonnull
    private List<BookingDTO> getBookingsByDate(@Nonnull final CourtDTO courtDTO,
                                               @Nonnull final LocalDate date) {
        final List<BookingDTO> allBookings = courtDTO.getBookings();
        return allBookings.stream().filter(booking -> booking.getDate().equals(date)).toList();
    }
}
