package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.BookingSettings;
import ch.fortylove.persistence.dto.mapper.BookingSettingsMapper;
import ch.fortylove.persistence.entity.BookingSettingsEntity;
import ch.fortylove.persistence.repository.BookingSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
public class BookingSettingsServiceImpl implements BookingSettingsService {

    @Nonnull private final BookingSettingsRepository bookingSettingsRepository;
    @Nonnull private final BookingSettingsMapper bookingSettingsMapper;

    @Autowired
    public BookingSettingsServiceImpl(@Nonnull final BookingSettingsRepository bookingSettingsRepository,
                                      @Nonnull final BookingSettingsMapper bookingSettingsMapper) {
        this.bookingSettingsRepository = bookingSettingsRepository;
        this.bookingSettingsMapper = bookingSettingsMapper;
    }

    @Nonnull
    @Override
    public BookingSettings create(@Nonnull final BookingSettings bookingSettings) {
        final List<BookingSettingsEntity> existingBookingSettings = bookingSettingsRepository.findAll();
        if (existingBookingSettings.isEmpty()) {
            final BookingSettingsEntity save = bookingSettingsRepository.save(bookingSettingsMapper.convert(bookingSettings));
            return bookingSettingsMapper.convert(save);
        }

        return bookingSettingsMapper.convert(existingBookingSettings.get(0));
    }

    @Nonnull
    @Override
    public BookingSettings getBookingSettings() {
        final List<BookingSettingsEntity> bookingSettings = bookingSettingsRepository.findAll();
        if (bookingSettings.isEmpty()) {
            throw new IllegalStateException("No booking settings.");
        } else if (bookingSettings.size() > 1) {
            throw new IllegalStateException("More than one booking settings.");
        }

        return bookingSettingsMapper.convert(bookingSettings.get(0));
    }
}
