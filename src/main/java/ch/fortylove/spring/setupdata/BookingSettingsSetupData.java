package ch.fortylove.spring.setupdata;

import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.service.BookingSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.time.LocalTime;

@Component
@Profile({"h2", "develop", "local"})
public class BookingSettingsSetupData {

    @Nonnull private final BookingSettingsService bookingSettingsService;

    @Autowired
    public BookingSettingsSetupData(@Nonnull final BookingSettingsService bookingSettingsService) {
        this.bookingSettingsService = bookingSettingsService;
    }

    public void createBookingSettings() {
        createBookingSettingsIfNotFound(16, LocalTime.of(8, 0));
    }

    @Transactional
    void createBookingSettingsIfNotFound(final int numberOfTimeslots,
                                         @Nonnull final LocalTime startTime) {
        if (bookingSettingsService.getBookingSettings().isEmpty()) {
            final BookingSettings bookingSettings = new BookingSettings(numberOfTimeslots, startTime);
            bookingSettingsService.create(bookingSettings);
        }
    }
}