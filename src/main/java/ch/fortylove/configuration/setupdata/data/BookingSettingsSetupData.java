package ch.fortylove.configuration.setupdata.data;

import ch.fortylove.configuration.setupdata.SetupData;
import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.service.BookingSettingsService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@SetupData
public class BookingSettingsSetupData implements ch.fortylove.configuration.setupdata.data.SetupData {

    @Nonnull private final BookingSettingsService bookingSettingsService;

    @Autowired
    public BookingSettingsSetupData(@Nonnull final BookingSettingsService bookingSettingsService) {
        this.bookingSettingsService = bookingSettingsService;
    }

    @Override
    public void createData() {
        final SortedSet<Timeslot> timeslots = new TreeSet<>(
                List.of(
                        new Timeslot(false, 0),
                        new Timeslot(false, 1),
                        new Timeslot(false, 2),
                        new Timeslot(false, 3),
                        new Timeslot(false, 4),
                        new Timeslot(false, 5),
                        new Timeslot(true, 6),
                        new Timeslot(true, 7),
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
                        new Timeslot(true, 21),
                        new Timeslot(false, 22),
                        new Timeslot(false, 23)
                ));

        createBookingSettingsIfNotFound(timeslots);
    }

    @Transactional
    private void createBookingSettingsIfNotFound(@Nonnull final SortedSet<Timeslot> timeslots) {
        final BookingSettings bookingSettings = new BookingSettings(timeslots);
        bookingSettingsService.create(bookingSettings);
    }
}