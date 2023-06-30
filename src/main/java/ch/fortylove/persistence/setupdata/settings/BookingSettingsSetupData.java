package ch.fortylove.persistence.setupdata.settings;

import ch.fortylove.persistence.dto.BookingSettingsDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.persistence.service.BookingSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

@Component
@Profile({"h2", "develop", "local", "production"})
public class BookingSettingsSetupData {

    @Nonnull private final BookingSettingsService bookingSettingsService;

    @Autowired
    public BookingSettingsSetupData(@Nonnull final BookingSettingsService bookingSettingsService) {
        this.bookingSettingsService = bookingSettingsService;
    }

    public void createBookingSettings() {
        final List<TimeSlotDTO> timeSlotDTOs = Arrays.asList(
                new TimeSlotDTO(0L, false, 0),
                new TimeSlotDTO(0L, false, 1),
                new TimeSlotDTO(0L, false, 2),
                new TimeSlotDTO(0L, false, 3),
                new TimeSlotDTO(0L, false, 4),
                new TimeSlotDTO(0L, false, 5),
                new TimeSlotDTO(0L, false, 6),
                new TimeSlotDTO(0L, false, 7),
                new TimeSlotDTO(0L, true, 8),
                new TimeSlotDTO(0L, true, 9),
                new TimeSlotDTO(0L, true, 10),
                new TimeSlotDTO(0L, true, 11),
                new TimeSlotDTO(0L, true, 12),
                new TimeSlotDTO(0L, true, 13),
                new TimeSlotDTO(0L, true, 14),
                new TimeSlotDTO(0L, true, 15),
                new TimeSlotDTO(0L, true, 16),
                new TimeSlotDTO(0L, true, 17),
                new TimeSlotDTO(0L, true, 18),
                new TimeSlotDTO(0L, true, 19),
                new TimeSlotDTO(0L, true, 20),
                new TimeSlotDTO(0L, false, 21),
                new TimeSlotDTO(0L, false, 22),
                new TimeSlotDTO(0L, false, 23)
        );

        createBookingSettingsIfNotFound(timeSlotDTOs);
    }

    @Transactional
    void createBookingSettingsIfNotFound(@Nonnull final List<TimeSlotDTO> timeSlotDTOs) {
        final BookingSettingsDTO bookingSettings = new BookingSettingsDTO(0L, timeSlotDTOs);
        bookingSettingsService.create(bookingSettings);
    }
}