package ch.fortylove.views.newgrid;

import ch.fortylove.persistence.service.CourtService;
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
@PageTitle("Platzübersicht neu")
@PermitAll
public class BookingOverviewView extends VerticalLayout implements AfterNavigationObserver {

    @Nonnull private final CourtService courtService;

    private OverviewComponent overviewComponent;

    @Autowired
    public BookingOverviewView(@Nonnull final CourtService courtService) {
        this.courtService = courtService;

        addClassNames(
                LumoUtility.Background.SUCCESS,
                LumoUtility.Padding.MEDIUM
        );
        setSizeFull();

        constructUI();
    }

    private void constructUI() {
        overviewComponent = new OverviewComponent();
        add(overviewComponent);
    }

    @Override
    public void afterNavigation(@Nonnull final AfterNavigationEvent event) {
        overviewComponent.build(courtService.findAll());
    }
}
