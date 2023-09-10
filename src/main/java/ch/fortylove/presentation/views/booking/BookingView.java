package ch.fortylove.presentation.views.booking;

import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.booking.articlecomponent.ArticleGrid;
import ch.fortylove.presentation.views.booking.bookingcomponent.BookingComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = BookingView.ROUTE, layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle(BookingView.PAGE_TITLE)
@PermitAll
public class BookingView extends VerticalLayout implements AfterNavigationObserver {

    @Nonnull public static final String ROUTE = "bookings";
    @Nonnull public static final String PAGE_TITLE = "Plätze";

    @Nonnull private final BookingComponent bookingComponent;
    @Nonnull private final ArticleGrid articleGrid;

    @Autowired
    public BookingView(@Nonnull final BookingComponent bookingComponent,
                       @Nonnull final ArticleGrid articleGrid) {
        this.bookingComponent = bookingComponent;
        this.articleGrid = articleGrid;

        addClassName("booking-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        add(articleGrid, bookingComponent);
    }

    @Override
    public void afterNavigation(@Nonnull final AfterNavigationEvent event) {
        articleGrid.refresh();
        bookingComponent.refresh();
    }
}
