package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
@Service
public class BookingServiceImpl implements BookingService {

    @Nonnull private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(@Nonnull final BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Nonnull
    @Override
    public Booking create(@Nonnull final Booking booking) {
        return bookingRepository.save(booking);
    }

    @Nonnull
    @Override
    public List<Booking> findAllByCourtId(final long courtId) {
        return bookingRepository.findAllByCourtId(courtId);
    }

}
