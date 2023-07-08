package ch.fortylove.persistence.setupdata.data;

import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.service.BookingSettingsService;
import ch.fortylove.persistence.setupdata.SetupData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@SetupData
public class BookingSettingsSetupData {

    @Nonnull private final BookingSettingsService bookingSettingsService;

    @Autowired
    public BookingSettingsSetupData(@Nonnull final BookingSettingsService bookingSettingsService) {
        this.bookingSettingsService = bookingSettingsService;
    }

    public void createBookingSettings() {
        final List<Timeslot> timeslots = Arrays.asList(
                new Timeslot(false, 0),
                new Timeslot(false, 1),
                new Timeslot(false, 2),
                new Timeslot(false, 3),
                new Timeslot(false, 4),
                new Timeslot(false, 5),
                new Timeslot(false, 6),
                new Timeslot(false, 7),
                new Timeslot(true, 8),
                new Timeslot(true, 9),
                new Timeslot(true, 10),
                new Timeslot(true, 11),
                new Timeslot(true, 12),
                new Timeslot(true, 13),
                new Timeslot(true, 14),
                new Timeslot(true, 15),
                new Timeslot(true, 16),
                new Timeslot(true, 17),
                new Timeslot(true, 18),
                new Timeslot(true, 19),
                new Timeslot(true, 20),
                new Timeslot(false, 21),
                new Timeslot(false, 22),
                new Timeslot(false, 23)
        );

        createBookingSettingsIfNotFound(timeslots);
    }

    @Transactional
    void createBookingSettingsIfNotFound(@Nonnull final List<Timeslot> timeslots) {
        final BookingSettings bookingSettings = new BookingSettings(timeslots);
        bookingSettingsService.create(bookingSettings);
    }
}