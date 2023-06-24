package ch.fortylove.persistence.service.settings;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@SpringTest
class TestBookingSettingsServiceImpl {

    @Autowired BookingSettingsService testee;

    @Test
    public void testCreate_nonExisting() {
        final BookingSettings bookingSettings = new BookingSettings(getTimeSlotsVersionA());

        final BookingSettings createdBookingSettings = testee.create(bookingSettings);

        Assertions.assertEquals(createdBookingSettings, bookingSettings);
    }

    @Test
    public void testCreate_existing() {
        final BookingSettings existingBookingSettings = new BookingSettings(getTimeSlotsVersionA());
        final BookingSettings newBookingSettings = new BookingSettings(getTimeSlotsVersionB());
        testee.create(existingBookingSettings);

        final BookingSettings createdBookingSettings = testee.create(newBookingSettings);

        Assertions.assertNotEquals(createdBookingSettings, newBookingSettings);
        Assertions.assertEquals(createdBookingSettings, existingBookingSettings);
    }

    @Nonnull
    private List<TimeSlot> getTimeSlotsVersionA() {
        return Arrays.asList(
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
                new TimeSlot(false,23)
        );
    }

    @Nonnull
    private List<TimeSlot> getTimeSlotsVersionB() {
        return Arrays.asList(
                new TimeSlot(false, 0),
                new TimeSlot(false, 1),
                new TimeSlot(false, 2),
                new TimeSlot(false, 3),
                new TimeSlot(false, 4),
                new TimeSlot(false, 5),
                new TimeSlot(false, 6),
                new TimeSlot(false, 7),
                new TimeSlot(false, 8),
                new TimeSlot(false, 9),
                new TimeSlot(false, 10),
                new TimeSlot(false, 11),
                new TimeSlot(true, 12),
                new TimeSlot(true, 13),
                new TimeSlot(true, 14),
                new TimeSlot(true, 15),
                new TimeSlot(true, 16),
                new TimeSlot(true, 17),
                new TimeSlot(false, 18),
                new TimeSlot(false, 19),
                new TimeSlot(false, 20),
                new TimeSlot(false, 21),
                new TimeSlot(false, 22),
                new TimeSlot(false, 23)
        );
    }
}