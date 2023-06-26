package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.mapper.BookingMapper;
import ch.fortylove.persistence.dto.mapper.CycleAvoidingMappingContext;
import ch.fortylove.persistence.entity.BookingEntity;
import ch.fortylove.persistence.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {

    @Nonnull private final BookingRepository bookingRepository;
    @Nonnull private final BookingMapper bookingMapper;

    @Autowired
    public BookingServiceImpl(@Nonnull final BookingRepository bookingRepository,
                              @Nonnull final BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @Nonnull
    @Override
    public BookingDTO create(@Nonnull final BookingDTO booking) {
        final BookingEntity bookingEntity = bookingRepository.save(bookingMapper.convert(booking, new CycleAvoidingMappingContext()));
        return bookingMapper.convert(bookingEntity, new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public List<BookingDTO> findAllByCourtId(final long courtId) {
        return bookingMapper.convert(bookingRepository.findAllByCourtId(courtId), new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public List<BookingDTO> findAll() {
        return bookingMapper.convert(bookingRepository.findAll(), new CycleAvoidingMappingContext());
    }
}
