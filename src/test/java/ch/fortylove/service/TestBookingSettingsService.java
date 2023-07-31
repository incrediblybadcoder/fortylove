package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.Timeslot;
import jakarta.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.SortedSet;

@SpringTest
class TestBookingSettingsService extends ServiceTest {

    @Nonnull private final BookingSettingsService testee;

    @Nonnull private SortedSet<Timeslot> timeslotsAllBookable;
    @Nonnull private SortedSet<Timeslot> timeslotsNonBookable;

    @Autowired
    public TestBookingSettingsService(@Nonnull final BookingSettingsService testee) {
        this.testee = testee;
    }

    @BeforeEach
    void setUp() {
        timeslotsAllBookable = getTestDataFactory().getTimeslotDataFactory().getDefaultBookable();
        timeslotsNonBookable = getTestDataFactory().getTimeslotDataFactory().getDefaultNonBookable();
    }

    @Test
    public void testCreate_nonExisting() {
        final BookingSettings createdBookingSettings = testee.create(new BookingSettings(timeslotsAllBookable));

        Assertions.assertEquals(createdBookingSettings, testee.getBookingSettings());
    }

    @Test
    public void testCreate_existing() {
        final BookingSettings existingBookingSettings = testee.create(new BookingSettings(timeslotsAllBookable));
        final BookingSettings newBookingSettings = new BookingSettings(timeslotsNonBookable);

        final BookingSettings createdBookingSettings = testee.create(newBookingSettings);

        Assertions.assertEquals(createdBookingSettings, existingBookingSettings);
    }
}