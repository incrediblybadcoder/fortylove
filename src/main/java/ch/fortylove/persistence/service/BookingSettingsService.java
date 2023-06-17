package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.BookingSettings;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface BookingSettingsService {

    @Nonnull
    BookingSettings create(@Nonnull final BookingSettings bookingSettings);

    @Nonnull
    Optional<BookingSettings> getBookingSettings();

    @Nonnull
    Optional<BookingSettings> update(@Nonnull final BookingSettings privilege);
}
