package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.TimeSlot;
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
        final BookingSettings createdBookingSettings = testee.create(new BookingSettings(0L, getTimeSlotsVersionA()));

        Assertions.assertEquals(createdBookingSettings, testee.getBookingSettings());
    }

    @Test
    public void testCreate_existing() {
        final BookingSettings existingBookingSettings = testee.create(new BookingSettings(0L, getTimeSlotsVersionA()));
        final BookingSettings newBookingSettings = new BookingSettings(0L, getTimeSlotsVersionB());

        final BookingSettings createdBookingSettings = testee.create(newBookingSettings);

        Assertions.assertEquals(createdBookingSettings, existingBookingSettings);
    }

    @Nonnull
    private List<TimeSlot> getTimeSlotsVersionA() {
        return Arrays.asList(
                new TimeSlot(0L,false, 0),
                new TimeSlot(0L,false, 1),
                new TimeSlot(0L,false, 2),
                new TimeSlot(0L,false, 3),
                new TimeSlot(0L,false, 4),
                new TimeSlot(0L,false, 5),
                new TimeSlot(0L,false, 6),
                new TimeSlot(0L,false, 7),
                new TimeSlot(0L,true, 8),
                new TimeSlot(0L,true, 9),
                new TimeSlot(0L,true, 10),
                new TimeSlot(0L,true, 11),
                new TimeSlot(0L,true, 12),
                new TimeSlot(0L,true, 13),
                new TimeSlot(0L,true, 14),
                new TimeSlot(0L,true, 15),
                new TimeSlot(0L,true, 16),
                new TimeSlot(0L,true, 17),
                new TimeSlot(0L,true, 18),
                new TimeSlot(0L,true, 19),
                new TimeSlot(0L,true, 20),
                new TimeSlot(0L,false, 21),
                new TimeSlot(0L,false, 22),
                new TimeSlot(0L,false,23)
        );
    }

    @Nonnull
    private List<TimeSlot> getTimeSlotsVersionB() {
        return Arrays.asList(
                new TimeSlot(0L, false, 0),
                new TimeSlot(0L, false, 1),
                new TimeSlot(0L, false, 2),
                new TimeSlot(0L, false, 3),
                new TimeSlot(0L, false, 4),
                new TimeSlot(0L, false, 5),
                new TimeSlot(0L, false, 6),
                new TimeSlot(0L, false, 7),
                new TimeSlot(0L, false, 8),
                new TimeSlot(0L, false, 9),
                new TimeSlot(0L, false, 10),
                new TimeSlot(0L, false, 11),
                new TimeSlot(0L, true, 12),
                new TimeSlot(0L, true, 13),
                new TimeSlot(0L, true, 14),
                new TimeSlot(0L, true, 15),
                new TimeSlot(0L, true, 16),
                new TimeSlot(0L, true, 17),
                new TimeSlot(0L, false, 18),
                new TimeSlot(0L, false, 19),
                new TimeSlot(0L, false, 20),
                new TimeSlot(0L, false, 21),
                new TimeSlot(0L, false, 22),
                new TimeSlot(0L, false, 23)
        );
    }
}