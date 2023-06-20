package ch.fortylove.views.bookingoverview;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.views.MainLayout;
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

    Grid<Court> bookingGrid;

    @Autowired
    public BookingOverviewView(@Nonnull final CourtService courtService) {
        this.courtService = courtService;

        addClassName("court-list-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        bookingGrid = new BookingGrid();
        add(bookingGrid);
    }

    @Override
    public void afterNavigation(@Nonnull final AfterNavigationEvent event) {
        bookingGrid.setItems(courtService.findAll());
    }
}
