package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.Booking;
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
    public Booking create(@Nonnull final Booking booking) {
        final BookingEntity bookingEntity = bookingRepository.save(bookingMapper.convert(booking, new CycleAvoidingMappingContext()));
        return bookingMapper.convert(bookingEntity, new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public List<Booking> findAllByCourtId(final long courtId) {
        return bookingMapper.convert(bookingRepository.findAllByCourtId(courtId), new CycleAvoidingMappingContext());
    }

    @Nonnull
    @Override
    public List<Booking> findAll() {
        return bookingMapper.convert(bookingRepository.findAll(), new CycleAvoidingMappingContext());
    }
}
