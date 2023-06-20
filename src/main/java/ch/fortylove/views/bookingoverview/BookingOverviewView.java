package ch.fortylove.views.bookingoverview;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.service.BookingService;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.UserService;
import ch.fortylove.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@Route(value = "/bookings", layout = MainLayout.class)
@PageTitle("Platzübersicht")
@PermitAll
public class BookingOverviewView extends VerticalLayout implements AfterNavigationObserver {

    @Nonnull private final CourtService courtService;
    @Nonnull private final UserService userService;
    @Nonnull private final BookingService bookingService;

    Grid<Court> bookingGrid;

    Button refreshButton = new Button("Refresh", click -> refreshGrid());

    private void refreshGrid() {
        bookingGrid.setItems(courtService.findAll());
    }

    @Autowired
    public BookingOverviewView(@Nonnull final CourtService courtService, @Nonnull final UserService userService, @Nonnull final BookingService bookingService) {
        this.courtService = courtService;
        this.userService = userService;
        this.bookingService = bookingService;

        addClassName("court-list-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        bookingGrid = new BookingGrid(userService, bookingService);
        add(bookingGrid, refreshButton);
    }

    @Override
    public void afterNavigation(@Nonnull final AfterNavigationEvent event) {
        bookingGrid.setItems(courtService.findAll());
    }

}
