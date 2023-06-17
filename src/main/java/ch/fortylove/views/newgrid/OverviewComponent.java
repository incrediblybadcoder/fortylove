package ch.fortylove.views.newgrid;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.settings.BookingSettingsService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Component
public class OverviewComponent extends VerticalLayout {

    @Nonnull final private CourtService courtService;
    @Nonnull final private BookingSettingsService bookingSettingsService;

    @Autowired
    public OverviewComponent(@Nonnull final CourtService courtService,
                             @Nonnull final BookingSettingsService bookingSettingsService) {
        this.courtService = courtService;
        this.bookingSettingsService = bookingSettingsService;

        addClassNames(
                LumoUtility.Background.ERROR
        );
        getStyle().set("overflow", "auto");
//        setSpacing(false);
//        setPadding(false);
    }

    public void build() {
        removeAll();

        final Optional<BookingSettings> bookingSettings = bookingSettingsService.getBookingSettings();
        bookingSettings.ifPresent(settings -> {
            add(new OverviewHeaderComponent(settings));

            final List<Court> courts = courtService.findAll();
            courts.forEach(court -> add(new CourtComponent(settings, court)));
        });
    }
}
