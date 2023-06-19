package ch.fortylove.views;

import ch.fortylove.security.SecurityService;
import ch.fortylove.views.booking.BookingView;
import ch.fortylove.views.bookingoverview.BookingOverviewView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MainLayout extends AppLayout {

    @Nonnull private final SecurityService securityService;

    private Tabs menu;

    public MainLayout(@Nonnull final SecurityService securityService) {
        this.securityService = securityService;

        setDrawerOpened(false);

        final H3 appName = new H3("fortylove");
        appName.addClassNames(LumoUtility.Margin.Left.MEDIUM, LumoUtility.Margin.Right.MEDIUM);
        appName.getStyle().set("left", "var(--lumo-space-l)");

        menu = createMenuTabs();

        addToNavbar(appName);
        addToNavbar(true, menu);
    }

    private Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        getAvailableTabs().forEach(tabs::add);
        return tabs;
    }

    private List<Tab> getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab(VaadinIcon.CALENDAR, "booking ref", BookingOverviewView.class));
        tabs.add(createTab(VaadinIcon.CALENDAR, "booking neu", BookingView.class));
        tabs.add(createTab(VaadinIcon.USERS, "user management", MemberManagementView.class));

        return tabs;
    }

    private static Tab createTab(@Nonnull final VaadinIcon icon,
                                 @Nonnull final String title,
                                 @Nonnull final Class<? extends Component> viewClass) {
        final RouterLink content = populateLink(new RouterLink("", viewClass), icon, title);

        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    @Nonnull
    private static <T extends HasComponents> T populateLink(@Nonnull final T link,
                                                            @Nonnull final VaadinIcon icon,
                                                            @Nonnull final String title) {
        link.add(icon.create());
        link.add(title);
        return link;
    }

//    private void createHeader() {
//        final H1 logo = new H1("fortylove");
//        logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);
//
//        final String userName = securityService.getAuthenticatedUser().getUsername();
//        final Button logoutButton = new Button("Log out " + userName, e -> securityService.logout());
//
//        var header = new HorizontalLayout(new DrawerToggle(), logo, logoutButton);
//
//        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
//        header.expand(logo);
//        header.setWidthFull();
//        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);
//
//        addToNavbar(header);
//
//    }
//
//    private void createDrawer() {
//        addToDrawer(new VerticalLayout(
//                new RouterLink("Platzübersicht-Ref", BookingOverviewView.class),
//                new RouterLink("Platzübersicht", BookingView.class),
//                new RouterLink("Benutzerverwaltung", MemberManagementView.class)
//        ));
//    }
}