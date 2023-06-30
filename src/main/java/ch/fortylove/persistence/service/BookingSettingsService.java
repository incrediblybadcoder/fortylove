package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.BookingSettings;

import javax.annotation.Nonnull;

public interface BookingSettingsService {

    @Nonnull
    BookingSettings create(@Nonnull final BookingSettings bookingSettings);

    @Nonnull
    BookingSettings getBookingSettings();
}
