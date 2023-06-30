package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.BookingSettingsDTO;

import javax.annotation.Nonnull;

public interface BookingSettingsService {

    @Nonnull
    BookingSettingsDTO create(@Nonnull final BookingSettingsDTO bookingSettingsDTO);

    @Nonnull
    BookingSettingsDTO getBookingSettings();
}
