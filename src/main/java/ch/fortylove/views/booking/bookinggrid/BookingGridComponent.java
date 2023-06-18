package ch.fortylove.views.booking.bookinggrid;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.settings.BookingSettingsService;
import ch.fortylove.views.booking.bookinggrid.rows.BookingGridHeaderRowComponent;
import ch.fortylove.views.booking.bookinggrid.rows.CourtRowComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;

@Component
public class BookingGridComponent extends VerticalLayout {

    @Nonnull final private CourtService courtService;
    @Nonnull final private BookingSettingsService bookingSettingsService;

    @Autowired
    public BookingGridComponent(@Nonnull final CourtService courtService,
                                @Nonnull final BookingSettingsService bookingSettingsService) {
        this.courtService = courtService;
        this.bookingSettingsService = bookingSettingsService;

        addClassNames(
                LumoUtility.Background.PRIMARY
        );
        getStyle().set("overflow", "auto");
//        setSpacing(false);
        setPadding(false);
    }

    public void build(@Nonnull final LocalDate date) {
        removeAll();

        // time axis
        final BookingSettings bookingSettings = bookingSettingsService.getBookingSettings();
        add(new BookingGridHeaderRowComponent(bookingSettings));

        // courts
        final List<Court> courts = courtService.findAll();
        courts.forEach(court -> add(new CourtRowComponent(bookingSettings, court)));

    }
}
