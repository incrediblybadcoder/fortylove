package ch.fortylove.persistence.setupdata.settings;

import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.TimeSlot;
import ch.fortylove.persistence.service.BookingSettingsService;
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
        final List<TimeSlot> TimeSlots = Arrays.asList(
                new TimeSlot(0L, false, 0),
                new TimeSlot(0L, false, 1),
                new TimeSlot(0L, false, 2),
                new TimeSlot(0L, false, 3),
                new TimeSlot(0L, false, 4),
                new TimeSlot(0L, false, 5),
                new TimeSlot(0L, false, 6),
                new TimeSlot(0L, false, 7),
                new TimeSlot(0L, true, 8),
                new TimeSlot(0L, true, 9),
                new TimeSlot(0L, true, 10),
                new TimeSlot(0L, true, 11),
                new TimeSlot(0L, true, 12),
                new TimeSlot(0L, true, 13),
                new TimeSlot(0L, true, 14),
                new TimeSlot(0L, true, 15),
                new TimeSlot(0L, true, 16),
                new TimeSlot(0L, true, 17),
                new TimeSlot(0L, true, 18),
                new TimeSlot(0L, true, 19),
                new TimeSlot(0L, true, 20),
                new TimeSlot(0L, false, 21),
                new TimeSlot(0L, false, 22),
                new TimeSlot(0L, false, 23)
        );

        createBookingSettingsIfNotFound(TimeSlots);
    }

    @Transactional
    void createBookingSettingsIfNotFound(@Nonnull final List<TimeSlot> TimeSlots) {
        final BookingSettings bookingSettings = new BookingSettings(0L, TimeSlots);
        bookingSettingsService.create(bookingSettings);
    }
}