package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.Court;
import ch.fortylove.persistence.dto.mapper.CourtMapper;
import ch.fortylove.persistence.dto.mapper.CycleAvoidingMappingContext;
import ch.fortylove.persistence.entity.CourtEntity;
import ch.fortylove.persistence.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.time.LocalDate;
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
    @Transactional
    public List<Court> findAll() {
        return courtMapper.convert(courtRepository.findAll(), new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public List<Court> findAllWithBookingsByDate(@Nonnull final LocalDate date) {
        return courtMapper.convert(courtRepository.findAllWithBookingsByDate(date), new CycleAvoidingMappingContext());
    }
}
