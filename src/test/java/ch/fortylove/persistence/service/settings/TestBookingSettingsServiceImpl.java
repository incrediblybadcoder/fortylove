package ch.fortylove.persistence.service.settings;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@SpringTest
class TestBookingSettingsServiceImpl {

    @Autowired BookingSettingsService testee;

    @Test
    public void testCreate_nonExistent() {
        final BookingSettings bookingSettings = new BookingSettings(getTimeSlotsVersionA());

        final BookingSettings createdBookingSettings = testee.create(bookingSettings);

        Assertions.assertEquals(createdBookingSettings, bookingSettings);
    }

    @Test
    public void testCreate_existent() {
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
                new TimeSlot(false, LocalTime.of(0, 0)),
                new TimeSlot(false, LocalTime.of(1, 0)),
                new TimeSlot(false, LocalTime.of(2, 0)),
                new TimeSlot(false, LocalTime.of(3, 0)),
                new TimeSlot(false, LocalTime.of(4, 0)),
                new TimeSlot(false, LocalTime.of(5, 0)),
                new TimeSlot(false, LocalTime.of(6, 0)),
                new TimeSlot(false, LocalTime.of(7, 0)),
                new TimeSlot(true, LocalTime.of(8, 0)),
                new TimeSlot(true, LocalTime.of(9, 0)),
                new TimeSlot(true, LocalTime.of(10, 0)),
                new TimeSlot(true, LocalTime.of(12, 0)),
                new TimeSlot(true, LocalTime.of(12, 0)),
                new TimeSlot(true, LocalTime.of(13, 0)),
                new TimeSlot(true, LocalTime.of(14, 0)),
                new TimeSlot(true, LocalTime.of(15, 0)),
                new TimeSlot(true, LocalTime.of(16, 0)),
                new TimeSlot(true, LocalTime.of(17, 0)),
                new TimeSlot(true, LocalTime.of(18, 0)),
                new TimeSlot(false, LocalTime.of(19, 0)),
                new TimeSlot(false, LocalTime.of(20, 0)),
                new TimeSlot(false, LocalTime.of(21, 0)),
                new TimeSlot(false, LocalTime.of(22, 0)),
                new TimeSlot(false, LocalTime.of(23, 0))
        );
    }

    @Nonnull
    private List<TimeSlot> getTimeSlotsVersionB() {
        return Arrays.asList(
                new TimeSlot(false, LocalTime.of(0, 0)),
                new TimeSlot(false, LocalTime.of(1, 0)),
                new TimeSlot(false, LocalTime.of(2, 0)),
                new TimeSlot(false, LocalTime.of(3, 0)),
                new TimeSlot(false, LocalTime.of(4, 0)),
                new TimeSlot(false, LocalTime.of(5, 0)),
                new TimeSlot(false, LocalTime.of(6, 0)),
                new TimeSlot(false, LocalTime.of(7, 0)),
                new TimeSlot(false, LocalTime.of(8, 0)),
                new TimeSlot(false, LocalTime.of(9, 0)),
                new TimeSlot(false, LocalTime.of(10, 0)),
                new TimeSlot(false, LocalTime.of(12, 0)),
                new TimeSlot(false, LocalTime.of(12, 0)),
                new TimeSlot(false, LocalTime.of(13, 0)),
                new TimeSlot(true, LocalTime.of(14, 0)),
                new TimeSlot(true, LocalTime.of(15, 0)),
                new TimeSlot(true, LocalTime.of(16, 0)),
                new TimeSlot(false, LocalTime.of(17, 0)),
                new TimeSlot(false, LocalTime.of(18, 0)),
                new TimeSlot(false, LocalTime.of(19, 0)),
                new TimeSlot(false, LocalTime.of(20, 0)),
                new TimeSlot(false, LocalTime.of(21, 0)),
                new TimeSlot(false, LocalTime.of(22, 0)),
                new TimeSlot(false, LocalTime.of(23, 0))
        );
    }
}