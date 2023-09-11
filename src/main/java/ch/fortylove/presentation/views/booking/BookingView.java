package ch.fortylove.presentation.views.booking;

import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.booking.articlegrid.ArticleGrid;
import ch.fortylove.presentation.views.booking.bookinggrid.BookingGrid;
import ch.fortylove.presentation.views.booking.dateselection.DateSelection;
import ch.fortylove.presentation.views.booking.dateselection.events.DateChangeEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Route(value = BookingView.ROUTE, layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle(BookingView.PAGE_TITLE)
@PermitAll
public class BookingView extends VerticalLayout implements AfterNavigationObserver {

    @Nonnull public static final String ROUTE = "bookings";
    @Nonnull public static final String PAGE_TITLE = "Plätze";

    @Nonnull private final ArticleGrid articleGrid;
    @Nonnull private final DateSelection dateSelection;
    @Nonnull private final BookingGrid bookingGrid;

    @Autowired
    public BookingView(@Nonnull final ArticleGrid articleGrid,
                       @Nonnull final DateSelection dateSelection,
                       @Nonnull final BookingGrid bookingGrid) {
        this.articleGrid = articleGrid;
        this.dateSelection = dateSelection;
        this.bookingGrid = bookingGrid;

        addClassName("booking-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        add(articleGrid, getDateSelection(), bookingGrid);
    }

    @Nonnull
    private DateSelection getDateSelection() {
        dateSelection.addDateChangeListener(this::dateChanged);
        return dateSelection;
    }

    private void dateChanged(@Nonnull final DateChangeEvent event) {
        refresh(event.getDate());
    }

    @Override
    public void afterNavigation(@Nonnull final AfterNavigationEvent event) {
        refresh(dateSelection.getDate());
    }

    private void refresh(@Nonnull final LocalDate date) {
        articleGrid.refresh();
        bookingGrid.refresh(date);
    }
}
