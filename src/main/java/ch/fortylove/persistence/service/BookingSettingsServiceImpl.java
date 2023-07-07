package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.repository.BookingSettingsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
@Transactional
public class BookingSettingsServiceImpl implements BookingSettingsService {

    @Nonnull private final BookingSettingsRepository bookingSettingsRepository;

    @Autowired
    public BookingSettingsServiceImpl(@Nonnull final BookingSettingsRepository bookingSettingsRepository) {
        this.bookingSettingsRepository = bookingSettingsRepository;
    }

    @Nonnull
    @Override
    public BookingSettings create(@Nonnull final BookingSettings bookingSettings) {
        final List<BookingSettings> existingBookingSettings = bookingSettingsRepository.findAll();
        if (existingBookingSettings.isEmpty()) {
            return bookingSettingsRepository.save(bookingSettings);
        }

        return existingBookingSettings.get(0);
    }

    @Nonnull
    @Override
    public BookingSettings getBookingSettings() {
        final List<BookingSettings> bookingSettings = bookingSettingsRepository.findAll();
        if (bookingSettings.isEmpty()) {
            throw new IllegalStateException("No booking settings.");
        } else if (bookingSettings.size() > 1) {
            throw new IllegalStateException("More than one booking settings.");
        }

        return bookingSettings.get(0);
    }
}
