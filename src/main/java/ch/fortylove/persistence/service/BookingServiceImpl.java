package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Nonnull private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(@Nonnull final BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Nonnull
    @Override
    public Booking create(@Nonnull final Booking booking){
        if (bookingRepository.findById(booking.getId()).isPresent() ||
                !bookingRepository.findAllByCourtAndTimeslotAndDate(booking.getCourt(), booking.getTimeslot(), booking.getDate()).isEmpty()) {
            throw new DuplicateRecordException(booking);
        }
        return bookingRepository.save(booking);
    }

    @Nonnull
    @Override
    public Booking update(@Nonnull final Booking booking) {
        if (bookingRepository.findById(booking.getId()).isEmpty()) {
            throw new RecordNotFoundException(booking);
        }
        return bookingRepository.save(booking);
    }

    @Nonnull
    @Override
    public Optional<Booking> findById(final long id) {
        return bookingRepository.findById(id);
    }

    @Nonnull
    @Override
    public List<Booking> findAllByCourtId(final long id) {
        return bookingRepository.findAllByCourtId(id);
    }

    @Override
    public void delete(final long id) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        booking.getOwner().getOwnerBookings().remove(booking);
        booking.removeOpponents();

        bookingRepository.delete(booking);
    }
}
