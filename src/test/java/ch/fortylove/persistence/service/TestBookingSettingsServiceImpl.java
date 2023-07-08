package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.Timeslot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@SpringTest
class TestBookingSettingsServiceImpl {

    @Autowired private BookingSettingsService testee;

    @Test
    public void testCreate_nonExisting() {
        final BookingSettings createdBookingSettings = testee.create(new BookingSettings(getTimeSlotsVersionA()));

        Assertions.assertEquals(createdBookingSettings, testee.getBookingSettings());
    }

    @Test
    public void testCreate_existing() {
        final BookingSettings existingBookingSettings = testee.create(new BookingSettings(getTimeSlotsVersionA()));
        final BookingSettings newBookingSettings = new BookingSettings(getTimeSlotsVersionB());

        final BookingSettings createdBookingSettings = testee.create(newBookingSettings);

        Assertions.assertEquals(createdBookingSettings, existingBookingSettings);
    }

    @Nonnull
    private List<Timeslot> getTimeSlotsVersionA() {
        return Arrays.asList(
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
                new Timeslot(false,23)
        );
    }

    @Nonnull
    private List<Timeslot> getTimeSlotsVersionB() {
        return Arrays.asList(
                new Timeslot(false, 0),
                new Timeslot(false, 1),
                new Timeslot(false, 2),
                new Timeslot(false, 3),
                new Timeslot(false, 4),
                new Timeslot(false, 5),
                new Timeslot(false, 6),
                new Timeslot(false, 7),
                new Timeslot(false, 8),
                new Timeslot(false, 9),
                new Timeslot(false, 10),
                new Timeslot(false, 11),
                new Timeslot(true, 12),
                new Timeslot(true, 13),
                new Timeslot(true, 14),
                new Timeslot(true, 15),
                new Timeslot(true, 16),
                new Timeslot(true, 17),
                new Timeslot(false, 18),
                new Timeslot(false, 19),
                new Timeslot(false, 20),
                new Timeslot(false, 21),
                new Timeslot(false, 22),
                new Timeslot(false, 23)
        );
    }
}