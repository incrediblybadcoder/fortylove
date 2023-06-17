package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.BookingSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.Optional;

@SpringTest
class TestBookingSettingsServiceImpl {

    @Autowired BookingSettingsService testee;

    @Test
    public void testCreate_nonExistent() {
        final BookingSettings bookingSettings = new BookingSettings(16, LocalTime.of(8,30));

        final BookingSettings createdBookingSettings = testee.create(bookingSettings);

        Assertions.assertEquals(createdBookingSettings, bookingSettings);
    }

    @Test
    public void testCreate_existent() {
        final BookingSettings existingBookingSettings = new BookingSettings(16, LocalTime.of(8,30));
        final BookingSettings newBookingSettings = new BookingSettings(10, LocalTime.of(12,10));
        testee.create(existingBookingSettings);

        final BookingSettings createdBookingSettings = testee.create(newBookingSettings);

        Assertions.assertNotEquals(createdBookingSettings, newBookingSettings);
        Assertions.assertEquals(createdBookingSettings, existingBookingSettings);
    }

    @Test
    public void testUpdate() {
        final BookingSettings existingBookingSettings = new BookingSettings(16, LocalTime.of(8,30));
        testee.create(existingBookingSettings);
        final BookingSettings newBookingSettings = new BookingSettings(10, LocalTime.of(12,10));

        final Optional<BookingSettings> updatedBookingSettings = testee.update(newBookingSettings);

        Assertions.assertTrue(updatedBookingSettings.isPresent());
        Assertions.assertEquals(newBookingSettings, updatedBookingSettings.get());
    }
}