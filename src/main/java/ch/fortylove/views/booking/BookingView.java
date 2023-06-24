package ch.fortylove.views.booking;

import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.settings.BookingSettingsService;
import ch.fortylove.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@Route(value = "/newbookings", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Platzübersicht")
@PermitAll
public class BookingView extends VerticalLayout implements AfterNavigationObserver {

    @Nonnull private final CourtService courtService;
    @Nonnull private final BookingSettingsService bookingSettingsService;

    private BookingComponent bookingComponent;

    @Autowired
    public BookingView(@Nonnull final CourtService courtService,
                       @Nonnull final BookingSettingsService bookingSettingsService) {
        this.courtService = courtService;
        this.bookingSettingsService = bookingSettingsService;

        addClassNames(LumoUtility.Padding.MEDIUM, "booking-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        add(createBookingComponent());
    }

    @Nonnull
    private BookingComponent createBookingComponent() {
        bookingComponent = new BookingComponent(courtService, bookingSettingsService);

        return bookingComponent;
    }

    @Override
    public void afterNavigation(@Nonnull final AfterNavigationEvent event) {
        bookingComponent.build();
    }
}
