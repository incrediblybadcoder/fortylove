package ch.fortylove.persistence.service.settings;

import ch.fortylove.persistence.entity.settings.BookingSettings;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface BookingSettingsService {

    @Nonnull
    BookingSettings create(@Nonnull final BookingSettings bookingSettings);

    @Nonnull
    Optional<BookingSettings> getBookingSettings();
}
