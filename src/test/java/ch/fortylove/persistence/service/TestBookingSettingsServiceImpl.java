package ch.fortylove.persistence.service;

import ch.fortylove.SpringTest;
import ch.fortylove.persistence.dto.BookingSettingsDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
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
        final BookingSettingsDTO createdBookingSettings = testee.create(new BookingSettingsDTO(0L, getTimeSlotsVersionA()));

        Assertions.assertEquals(createdBookingSettings, testee.getBookingSettings());
    }

    @Test
    public void testCreate_existing() {
        final BookingSettingsDTO existingBookingSettings = testee.create(new BookingSettingsDTO(0L, getTimeSlotsVersionA()));
        final BookingSettingsDTO newBookingSettings = new BookingSettingsDTO(0L, getTimeSlotsVersionB());

        final BookingSettingsDTO createdBookingSettings = testee.create(newBookingSettings);

        Assertions.assertEquals(createdBookingSettings, existingBookingSettings);
    }

    @Nonnull
    private List<TimeSlotDTO> getTimeSlotsVersionA() {
        return Arrays.asList(
                new TimeSlotDTO(0L,false, 0),
                new TimeSlotDTO(0L,false, 1),
                new TimeSlotDTO(0L,false, 2),
                new TimeSlotDTO(0L,false, 3),
                new TimeSlotDTO(0L,false, 4),
                new TimeSlotDTO(0L,false, 5),
                new TimeSlotDTO(0L,false, 6),
                new TimeSlotDTO(0L,false, 7),
                new TimeSlotDTO(0L,true, 8),
                new TimeSlotDTO(0L,true, 9),
                new TimeSlotDTO(0L,true, 10),
                new TimeSlotDTO(0L,true, 11),
                new TimeSlotDTO(0L,true, 12),
                new TimeSlotDTO(0L,true, 13),
                new TimeSlotDTO(0L,true, 14),
                new TimeSlotDTO(0L,true, 15),
                new TimeSlotDTO(0L,true, 16),
                new TimeSlotDTO(0L,true, 17),
                new TimeSlotDTO(0L,true, 18),
                new TimeSlotDTO(0L,true, 19),
                new TimeSlotDTO(0L,true, 20),
                new TimeSlotDTO(0L,false, 21),
                new TimeSlotDTO(0L,false, 22),
                new TimeSlotDTO(0L,false,23)
        );
    }

    @Nonnull
    private List<TimeSlotDTO> getTimeSlotsVersionB() {
        return Arrays.asList(
                new TimeSlotDTO(0L, false, 0),
                new TimeSlotDTO(0L, false, 1),
                new TimeSlotDTO(0L, false, 2),
                new TimeSlotDTO(0L, false, 3),
                new TimeSlotDTO(0L, false, 4),
                new TimeSlotDTO(0L, false, 5),
                new TimeSlotDTO(0L, false, 6),
                new TimeSlotDTO(0L, false, 7),
                new TimeSlotDTO(0L, false, 8),
                new TimeSlotDTO(0L, false, 9),
                new TimeSlotDTO(0L, false, 10),
                new TimeSlotDTO(0L, false, 11),
                new TimeSlotDTO(0L, true, 12),
                new TimeSlotDTO(0L, true, 13),
                new TimeSlotDTO(0L, true, 14),
                new TimeSlotDTO(0L, true, 15),
                new TimeSlotDTO(0L, true, 16),
                new TimeSlotDTO(0L, true, 17),
                new TimeSlotDTO(0L, false, 18),
                new TimeSlotDTO(0L, false, 19),
                new TimeSlotDTO(0L, false, 20),
                new TimeSlotDTO(0L, false, 21),
                new TimeSlotDTO(0L, false, 22),
                new TimeSlotDTO(0L, false, 23)
        );
    }
}