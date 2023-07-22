package ch.fortylove.view.booking;

import ch.fortylove.view.MainLayout;
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

@Route(value = "/bookings", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Platzübersicht")
@PermitAll
public class BookingView extends VerticalLayout implements AfterNavigationObserver {

    @Nonnull private final BookingComponent bookingComponent;

    @Autowired
    public BookingView(@Nonnull final BookingComponent bookingComponent) {
        this.bookingComponent = bookingComponent;

        addClassNames(LumoUtility.Padding.MEDIUM, "booking-view");
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        add(bookingComponent);
    }

    @Override
    public void afterNavigation(@Nonnull final AfterNavigationEvent event) {
        bookingComponent.refresh();
    }
}
