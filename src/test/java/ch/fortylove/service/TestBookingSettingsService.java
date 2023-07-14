package ch.fortylove.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.Timeslot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.List;

@SpringTest
class TestBookingSettingsService extends ServiceTest {

    @Nonnull private final BookingSettingsService testee;

    @Nonnull private List<Timeslot> timeslotsAllBookable;
    @Nonnull private List<Timeslot> timeslotsNonBookable;

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