package ch.fortylove.persistence.setupdata.settings;

import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.persistence.service.settings.BookingSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@Component
@Profile({"h2", "develop", "local", "production"})
public class BookingSettingsSetupData {

    @Nonnull private final BookingSettingsService bookingSettingsService;

    @Autowired
    public BookingSettingsSetupData(@Nonnull final BookingSettingsService bookingSettingsService) {
        this.bookingSettingsService = bookingSettingsService;
    }

    public void createBookingSettings() {
        final List<TimeSlot> timeSlots = Arrays.asList(
                new TimeSlot(false, 0),
                new TimeSlot(false, 1),
                new TimeSlot(false, 2),
                new TimeSlot(false, 3),
                new TimeSlot(false, 4),
                new TimeSlot(false, 5),
                new TimeSlot(false, 6),
                new TimeSlot(false, 7),
                new TimeSlot(true, 8),
                new TimeSlot(true, 9),
                new TimeSlot(true, 10),
                new TimeSlot(true, 11),
                new TimeSlot(true, 12),
                new TimeSlot(true, 13),
                new TimeSlot(true, 14),
                new TimeSlot(true, 15),
                new TimeSlot(true, 16),
                new TimeSlot(true, 17),
                new TimeSlot(true, 18),
                new TimeSlot(true, 19),
                new TimeSlot(true, 20),
                new TimeSlot(false, 21),
                new TimeSlot(false, 22),
                new TimeSlot(false, 23)
        );

        createBookingSettingsIfNotFound(timeSlots);
    }

    @Transactional
    void createBookingSettingsIfNotFound(@Nonnull final List<TimeSlot> timeSlots) {
        final BookingSettings bookingSettings = new BookingSettings(timeSlots);
        bookingSettingsService.create(bookingSettings);
    }
}