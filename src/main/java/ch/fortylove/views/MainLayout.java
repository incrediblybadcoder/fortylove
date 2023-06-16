package ch.fortylove.views;

import ch.fortylove.security.SecurityService;
import ch.fortylove.views.bookingoverview.BookingOverviewView;
import ch.fortylove.views.membermanagementview.MemberManagementView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;

public class MainLayout extends AppLayout {

    @Nonnull private final SecurityService securityService;

    public MainLayout(@Nonnull final SecurityService securityService) {
        this.securityService = securityService;

        createHeader();
        createDrawer();
    }

    private void createHeader() {
        final H1 logo = new H1("fortylove");
        logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

        final String userName = securityService.getAuthenticatedUser().getUsername();
        final Button logoutButton = new Button("Log out " + userName, e -> securityService.logout());

        var header = new HorizontalLayout(new DrawerToggle(), logo, logoutButton);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink("Platzübersicht", BookingOverviewView.class),
                new RouterLink("Benutzerverwaltung", MemberManagementView.class)
        ));
    }
}