package ch.fortylove.views.booking;

import ch.fortylove.persistence.dto.Court;
import ch.fortylove.persistence.dto.User;
import ch.fortylove.persistence.service.BookingSettingsService;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.UserService;
import ch.fortylove.views.MainLayout;
import ch.fortylove.views.booking.events.BookingEvent;
import ch.fortylove.views.booking.events.GridRefreshEvent;
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
import java.time.LocalDate;
import java.util.List;

@Route(value = "/newbookings", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Platzübersicht")
@PermitAll
public class BookingView extends VerticalLayout implements AfterNavigationObserver {

    @Nonnull private final CourtService courtService;
    @Nonnull private final BookingSettingsService bookingSettingsService;
    @Nonnull private final UserService userService;

    private BookingComponent bookingComponent;

    @Autowired
    public BookingView(@Nonnull final CourtService courtService,
                       @Nonnull final BookingSettingsService bookingSettingsService,
                       @Nonnull final UserService userService) {
        this.courtService = courtService;
        this.bookingSettingsService = bookingSettingsService;
        this.userService = userService;

        addClassNames(LumoUtility.Padding.MEDIUM, "booking-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        add(createBookingComponent());
    }

    @Nonnull
    private BookingComponent createBookingComponent() {
        final BookingComponentConfiguration bookingComponentConfiguration = new BookingComponentConfiguration(bookingSettingsService.getBookingSettings().getTimeSlots());
        bookingComponent = new BookingComponent(bookingComponentConfiguration);
        bookingComponent.addGridRefreshListener(this::gridRefreshListener);
        bookingComponent.addBookingListener(this::bookingListener);

        return bookingComponent;
    }

    private void bookingListener(@Nonnull final BookingEvent bookingEvent) {
        System.out.println(bookingEvent);
    }

    private void gridRefreshListener(@Nonnull final GridRefreshEvent gridRefreshEvent) {
        refreshView(gridRefreshEvent.getDate());
    }

    @Override
    public void afterNavigation(@Nonnull final AfterNavigationEvent event) {
        refreshView(LocalDate.now());
    }

    private void refreshView(@Nonnull final LocalDate date) {
        final List<Court> courts = courtService.findAllByDate(date);
        final List<User> users = userService.findAll();

        bookingComponent.refreshComponent(courts, users);
    }
}
