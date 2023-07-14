package ch.fortylove.testdata.factory;

import ch.fortylove.persistence.entity.BookingSettings;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.service.BookingSettingsServiceImpl;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.List;

@SpringComponent
public class BookingSettingsTestDataFactory {

    @Nonnull private final BookingSettingsServiceImpl bookingSettingsService;
    @Nonnull private final TimeslotTestDataFactory timeslotTestDataFactory;

    @Autowired
    public BookingSettingsTestDataFactory(@Nonnull final BookingSettingsServiceImpl bookingSettingsService,
                                          @Nonnull final TimeslotTestDataFactory timeslotTestDataFactory) {
        this.bookingSettingsService = bookingSettingsService;
        this.timeslotTestDataFactory = timeslotTestDataFactory;
    }

    @Nonnull
    public BookingSettings createBookingSettings(@Nonnull final List<Timeslot> timeslots) {
        return bookingSettingsService.create(new BookingSettings(timeslots));
    }

    @Nonnull
    public BookingSettings getDefault() {
        return createBookingSettings(timeslotTestDataFactory.getDefaultBookable());
    }
}
