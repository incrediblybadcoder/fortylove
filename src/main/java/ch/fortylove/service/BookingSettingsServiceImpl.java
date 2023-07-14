package ch.fortylove.service;

import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.repository.BookingSettingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
@Transactional
public class BookingSettingsServiceImpl {

    @Nonnull private final BookingSettingsRepository bookingSettingsRepository;

    @Autowired
    public BookingSettingsServiceImpl(@Nonnull final BookingSettingsRepository bookingSettingsRepository) {
        this.bookingSettingsRepository = bookingSettingsRepository;
    }

    @Nonnull
    public BookingSettings create(@Nonnull final BookingSettings bookingSettings) {
        final List<BookingSettings> existingBookingSettings = bookingSettingsRepository.findAll();
        if (existingBookingSettings.isEmpty()) {
            return bookingSettingsRepository.save(bookingSettings);
        }

        return existingBookingSettings.get(0);
    }

    @Nonnull
    public BookingSettings getBookingSettings() {
        final List<BookingSettings> bookingSettings = bookingSettingsRepository.findAll();
        if (bookingSettings.isEmpty()) {
            throw new IllegalStateException("No booking data.");
        } else if (bookingSettings.size() > 1) {
            throw new IllegalStateException("More than one booking data.");
        }

        return bookingSettings.get(0);
    }
}
