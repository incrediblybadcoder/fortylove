package ch.fortylove.service;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.repository.BookingRepository;
import ch.fortylove.service.util.DatabaseResult;
import ch.fortylove.service.util.ValidationResult;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BookingService {

    @Nonnull private final BookingRepository bookingRepository;
    @Nonnull private final TimeSlotService timeSlotService;

    @Autowired
    public BookingService(@Nonnull final BookingRepository bookingRepository,
                          @Nonnull final TimeSlotService timeSlotService) {
        this.bookingRepository = bookingRepository;
        this.timeSlotService = timeSlotService;
    }

    @Nonnull
    public DatabaseResult<Booking> create(@Nonnull final Booking booking) {
        final ValidationResult validationResult = isUserBookingAllowedOnDate(booking);
        if (!validationResult.isSuccessful()) {
            return new DatabaseResult<>(validationResult.getMessage());
        }

        if (bookingRepository.findById(booking.getId()).isPresent() ||
                !bookingRepository.findAllByCourtAndTimeslotAndDate(booking.getCourt(), booking.getTimeslot(), booking.getDate()).isEmpty()) {
            return new DatabaseResult<>("Buchung existiert bereits: " + booking.getIdentifier());
        }

        return new DatabaseResult<>(bookingRepository.save(booking));
    }

    private int getUserBookingsOnDay(@Nonnull final User user,
                                     @Nonnull final LocalDate date) {
        return bookingRepository.getUserBookingsOnDay(user, date);
    }

    @Nonnull
    public DatabaseResult<Booking> update(@Nonnull final Booking booking) {
        if (bookingRepository.findById(booking.getId()).isEmpty()) {
            return new DatabaseResult<>("Buchung existiert nicht: " + booking.getIdentifier());
        }
        return new DatabaseResult<>(bookingRepository.save(booking));
    }

    @Nonnull
    public Optional<Booking> findById(@Nonnull final UUID id) {
        return bookingRepository.findById(id);
    }

    @Nonnull
    public List<Booking> findAllByCourtId(@Nonnull final UUID id) {
        return bookingRepository.findAllByCourtId(id);
    }

    @Nonnull
    public DatabaseResult<UUID> delete(@Nonnull final UUID id) {
        final Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isEmpty()) {
            return new DatabaseResult<>("Buchung existiert nicht: " + id);
        }

        final Booking booking = bookingOptional.get();
        booking.getOwner().getOwnerBookings().remove(booking);
        booking.removeAllOpponents();
        bookingRepository.delete(booking);

        return new DatabaseResult<>(id);
    }

    @Nonnull
    public ValidationResult isBookingModifiableOnDate(@Nonnull final Booking booking) {
        return isBookingModifiableOnDateInternal(booking, LocalDateTime.now());
    }

    @Nonnull
    protected ValidationResult isBookingModifiableOnDateInternal(@Nonnull final Booking booking,
                                                                 @Nonnull final LocalDateTime currentDateTime) {
        if (timeSlotService.isInPast(currentDateTime, booking.getDate(), booking.getTimeslot())) {
            return ValidationResult.failure("Datum liegt in der Vergangenheit");
        }

        return ValidationResult.success();
    }

    @Nonnull
    public ValidationResult isUserBookingAllowedOnDate(@Nonnull final Booking booking) {
        final LocalDate date = booking.getDate();
        final User owner = booking.getOwner();

        ValidationResult validationResult = isBookingCreatableOnDate(booking.getCourt(), booking.getOwner(), booking.getTimeslot(), date, LocalDateTime.now());
        if (!validationResult.isSuccessful()) {
            return validationResult;
        }

        final List<User> users = new ArrayList<>(booking.getOpponents());
        users.add(owner);

        validationResult = isOnlyUserBookingInTimeslot(users, booking.getTimeslot(), date);
        if (!validationResult.isSuccessful()) {
            return validationResult;
        }

        validationResult = isUserBookingAmountOverLimit(users, date);
        if (!validationResult.isSuccessful()) {
            return validationResult;
        }

        return ValidationResult.success();
    }

    @Nonnull
    private ValidationResult isOnlyUserBookingInTimeslot(@Nonnull final List<User> users,
                                                         @Nonnull final Timeslot timeslot,
                                                         @Nonnull final LocalDate date) {
        final List<Booking> bookings = bookingRepository.findAllByTimeslotAndDate(timeslot, date);

        for (final User user : users) {
            for (final Booking booking : bookings) {
                if (booking.getOwner().equals(user) || booking.getOpponents().contains(user)) {
                    return ValidationResult.failure(user.getIdentifier() + " hat bereits eine Buchung im selben Zeitraum");
                }
            }
        }

        return ValidationResult.success();
    }

    @Nonnull
    protected ValidationResult isBookingCreatableOnDate(@Nonnull final Court court,
                                                        @Nonnull final User user,
                                                        @Nonnull final Timeslot timeslot,
                                                        @Nonnull final LocalDate date,
                                                        @Nonnull final LocalDateTime currentDateTime) {
        if (bookingRepository.findAllByCourtAndTimeslotAndDate(court, timeslot, date).size() != 0) {
            return ValidationResult.failure("Duplikate Buchung");
        }

        if (timeSlotService.isInPast(currentDateTime, date, timeslot)) {
            return ValidationResult.failure("Datum liegt in der Vergangenheit");
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

    @Nonnull
    private ValidationResult isUserBookingAmountOverLimit(@Nonnull final List<User> users,
                                                          @Nonnull final LocalDate date) {
        for (final User user : users) {
            final int userBookingsOnDay = getUserBookingsOnDay(user, date);
            final int allowedBookingsPerDay = user.getPlayerStatus().getBookingsPerDay();

            if (userBookingsOnDay >= allowedBookingsPerDay) {
                return ValidationResult.failure("Buchungslimit pro Tag für " + user.getIdentifier() + " erreicht");
            }
        }

        return ValidationResult.success();
    }

    public boolean isBookingSlotActive(@Nonnull final Timeslot timeslot,
                                       @Nonnull final LocalDate date,
                                       @Nonnull final User currentUser,
                                       @Nullable final Booking booking) {
        // is not booking of user
        if (booking != null && !booking.getOwner().equals(currentUser)) {
            return false;
        }

        // is free cell and in past
        if (booking == null && timeSlotService.isInPast(LocalDateTime.now(), date, timeslot)) {
            return false;
        }

        if (isOutsideOfBookableDaysInAdvance(currentUser.getPlayerStatus(), date)) {
            return false;
        }

        return true;
    }
}
