package ch.fortylove.spring.setupdata.settings;

import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.persistence.service.settings.BookingSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
@Profile({"h2", "develop", "local"})
public class BookingSettingsSetupData {

    @Nonnull private final BookingSettingsService bookingSettingsService;

    @Autowired
    public BookingSettingsSetupData(@Nonnull final BookingSettingsService bookingSettingsService) {
        this.bookingSettingsService = bookingSettingsService;
    }

    public void createBookingSettings() {
        final List<TimeSlot> timeSlots = Arrays.asList(
                new TimeSlot(false, LocalTime.of(0,0)),
                new TimeSlot(false, LocalTime.of(1,0)),
                new TimeSlot(false, LocalTime.of(2,0)),
                new TimeSlot(false, LocalTime.of(3,0)),
                new TimeSlot(false, LocalTime.of(4,0)),
                new TimeSlot(false, LocalTime.of(5,0)),
                new TimeSlot(false, LocalTime.of(6,0)),
                new TimeSlot(false, LocalTime.of(7,0)),
                new TimeSlot(true, LocalTime.of(8,0)),
                new TimeSlot(true, LocalTime.of(9,0)),
                new TimeSlot(true, LocalTime.of(10,0)),
                new TimeSlot(true, LocalTime.of(12,0)),
                new TimeSlot(true, LocalTime.of(12,0)),
                new TimeSlot(true, LocalTime.of(13,0)),
                new TimeSlot(true, LocalTime.of(14,0)),
                new TimeSlot(true, LocalTime.of(15,0)),
                new TimeSlot(true, LocalTime.of(16,0)),
                new TimeSlot(true, LocalTime.of(17,0)),
                new TimeSlot(true, LocalTime.of(18,0)),
                new TimeSlot(false, LocalTime.of(19,0)),
                new TimeSlot(false, LocalTime.of(20,0)),
                new TimeSlot(false, LocalTime.of(21,0)),
                new TimeSlot(false, LocalTime.of(22,0)),
                new TimeSlot(false, LocalTime.of(23,0))
        );
        createBookingSettingsIfNotFound(timeSlots);
    }

    @Transactional
    void createBookingSettingsIfNotFound(@Nonnull final List<TimeSlot> timeSlots) {
        if (bookingSettingsService.getBookingSettings().isEmpty()) {
            final BookingSettings bookingSettings = new BookingSettings(timeSlots);
            bookingSettingsService.create(bookingSettings);
        }
    }
}