package ch.fortylove.service;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.persistence.error.RecordNotFoundException;
import ch.fortylove.persistence.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BookingService {

    @Nonnull private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(@Nonnull final BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Nonnull
    public Booking create(@Nonnull final Booking booking) {
        if (bookingRepository.findById(booking.getId()).isPresent() ||
                !bookingRepository.findAllByCourtAndTimeslotAndDate(booking.getCourt(), booking.getTimeslot(), booking.getDate()).isEmpty()) {
            throw new DuplicateRecordException(booking);
        }
        return bookingRepository.save(booking);
    }

    private int getUserBookingsOnDay(@Nonnull final User user,
                                     @Nonnull final LocalDate date) {
        return bookingRepository.getUserBookingsOnDay(user, date);
    }

    @Nonnull
    public Booking update(@Nonnull final Booking booking) {
        if (bookingRepository.findById(booking.getId()).isEmpty()) {
            throw new RecordNotFoundException(booking);
        }
        return bookingRepository.save(booking);
    }

    @Nonnull
    public Optional<Booking> findById(@Nonnull final UUID id) {
        return bookingRepository.findById(id);
    }

    @Nonnull
    public List<Booking> findAllByCourtId(@Nonnull final UUID id) {
        return bookingRepository.findAllByCourtId(id);
    }

    public void delete(@Nonnull final UUID id) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        booking.getOwner().getOwnerBookings().remove(booking);
        booking.removeOpponents();

        bookingRepository.delete(booking);
    }

    @Nonnull
    public ValidationResult isBookingModifiableOnDate(@Nonnull final Booking booking) {
        if (isInPast(booking.getDate(), booking.getTimeslot())) {
            return ValidationResult.failure("Datum liegt in der Vergangenheit");
        }

        return ValidationResult.success();
    }

    @Nonnull
    public ValidationResult isBookingCreatableOnDate(@Nonnull final Court court,
                                                     @Nonnull final Timeslot timeslot,
                                                     @Nonnull final LocalDate date) {
        if (bookingRepository.findAllByCourtAndTimeslotAndDate(court, timeslot, date).size() != 0) {
            return ValidationResult.failure("Duplikate Buchung");
        }
        if (isInPast(date, timeslot)) {
            return ValidationResult.failure("Datum liegt in der Vergangenheit");
        }

        return ValidationResult.success();
    }

    private boolean isInPast(@Nonnull final LocalDate date,
                             @Nonnull final Timeslot timeslot) {
        final LocalDate today = LocalDate.now();
        final boolean isPastDate = date.isBefore(today);
        final boolean isToday = date.equals(today);
        final boolean isPastTime = LocalTime.now().isAfter(timeslot.getStartTime());
        return isPastDate || (isToday && isPastTime);
    }

    @Nonnull
    public ValidationResult isUserBookingAllowedOnDate(@Nonnull final User user,
                                                       @Nonnull final LocalDate date) {
        if (isUserBookingAmountOverLimit(user, date)) {
            return ValidationResult.failure("Tägliches Buchungslimit erreicht");
        }
        if (isOutsideOfBookableDaysInAdvance(user.getPlayerStatus(), date)) {
            return ValidationResult.failure("Buchung liegt ausserhalb des erlaubten Bereichs");
        }

        return ValidationResult.success();
    }

    private boolean isOutsideOfBookableDaysInAdvance(@Nonnull final PlayerStatus playerStatus,
                                                     @Nonnull final LocalDate date) {
        final LocalDate maxAllowedDateInAdvance = LocalDate.now().plusDays(playerStatus.getBookableDaysInAdvance());
        return date.isAfter(maxAllowedDateInAdvance);
    }

    private boolean isUserBookingAmountOverLimit(@Nonnull final User user,
                                                 @Nonnull final LocalDate date) {
        final int userBookingsOnDay = getUserBookingsOnDay(user, date);
        final int allowedBookingsPerDay = user.getPlayerStatus().getBookingsPerDay();

        return userBookingsOnDay >= allowedBookingsPerDay;
    }
}
