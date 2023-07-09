package ch.fortylove.service;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    @Nonnull
    Booking create(@Nonnull final Booking booking);

    @Nonnull
    Booking update(@Nonnull final Booking booking);

    @Nonnull
    Optional<Booking> findById(final long id);

    @Nonnull
    List<Booking> findAllByCourtId(final long id);

    void delete(final long id);

    @Nonnull
    ValidationResult isBookingModifiable(@Nonnull final User user, @Nonnull final Booking booking);

    @Nonnull
    ValidationResult isBookingCreatableOnDate(@Nonnull final Court court, @Nonnull final Timeslot timeslot, @Nonnull final LocalDate date);

    @Nonnull
    ValidationResult isUserBookingAllowedOnDate(@Nonnull final User user, @Nonnull final LocalDate date);
}
