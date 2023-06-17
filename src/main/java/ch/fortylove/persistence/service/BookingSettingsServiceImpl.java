package ch.fortylove.persistence.service;

import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.repository.BookingSettingsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Service
public class BookingSettingsServiceImpl implements BookingSettingsService {

    @Nonnull private final BookingSettingsRepository bookingSettingsRepository;

    @Autowired
    public BookingSettingsServiceImpl(@Nonnull final BookingSettingsRepository bookingSettingsRepository) {
        this.bookingSettingsRepository = bookingSettingsRepository;
    }

    @Nonnull
    @Override
    public BookingSettings create(@Nonnull final BookingSettings bookingSettings) {
        return getBookingSettings().orElse(bookingSettingsRepository.save(bookingSettings));
    }

    @Nonnull
    @Override
    public Optional<BookingSettings> getBookingSettings() {
        final List<BookingSettings> bookingSettings = bookingSettingsRepository.findAll();
        return bookingSettings.isEmpty() ? Optional.empty() : Optional.of(bookingSettings.get(0));
    }

    @Nonnull
    @Override
    public Optional<BookingSettings> update(@Nonnull final BookingSettings bookingSettings) {
        final Optional<BookingSettings> existingBookingSettingsOptional = getBookingSettings();

        if (existingBookingSettingsOptional.isPresent()) {
            final BookingSettings existingBookingSettings = existingBookingSettingsOptional.get();
            BeanUtils.copyProperties(bookingSettings, existingBookingSettings, "id");
            return Optional.of(create(existingBookingSettings));
        } else {
            return Optional.empty();
        }
    }
}
