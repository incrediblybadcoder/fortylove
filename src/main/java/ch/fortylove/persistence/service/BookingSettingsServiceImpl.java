package ch.fortylove.persistence.service;

import ch.fortylove.persistence.dto.BookingSettingsDTO;
import ch.fortylove.persistence.dto.mapper.BookingSettingsMapper;
import ch.fortylove.persistence.entity.BookingSettings;
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
    public BookingSettingsDTO create(@Nonnull final BookingSettingsDTO bookingSettingsDTO) {
        final List<BookingSettings> existingBookingSettings = bookingSettingsRepository.findAll();
        if (existingBookingSettings.isEmpty()) {
            final BookingSettings save = bookingSettingsRepository.save(bookingSettingsMapper.convert(bookingSettingsDTO));
            return bookingSettingsMapper.convert(save);
        }

        return bookingSettingsMapper.convert(existingBookingSettings.get(0));
    }

    @Nonnull
    @Override
    public BookingSettingsDTO getBookingSettings() {
        final List<BookingSettings> bookingSettings = bookingSettingsRepository.findAll();
        if (bookingSettings.isEmpty()) {
            throw new IllegalStateException("No booking settings.");
        } else if (bookingSettings.size() > 1) {
            throw new IllegalStateException("More than one booking settings.");
        }

        return bookingSettingsMapper.convert(bookingSettings.get(0));
    }
}
