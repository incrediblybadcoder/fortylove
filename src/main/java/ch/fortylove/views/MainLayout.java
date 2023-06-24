package ch.fortylove.views;

import ch.fortylove.security.SecurityService;
import ch.fortylove.views.booking.BookingView;
import ch.fortylove.views.membermanagement.MemberManagementView;
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

    public MainLayout(@Nonnull final SecurityService securityService) {
        this.securityService = securityService;

        setDrawerOpened(false);

        final H3 appName = new H3("fortylove");
        appName.addClassNames(LumoUtility.Margin.Left.MEDIUM, LumoUtility.Margin.Right.MEDIUM);
        appName.getStyle().set("left", "var(--lumo-space-l)");

        final Tabs menu = createMenuTabs();

        addToNavbar(appName);
        addToNavbar(true, menu);
    }

    @Nonnull
    private Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        getAvailableTabs().forEach(tabs::add);
        return tabs;
    }

    @Nonnull
    private List<Tab> getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab(VaadinIcon.CALENDAR, "booking", BookingView.class));
        tabs.add(createTab(VaadinIcon.CALENDAR, "booking neu", ch.fortylove.views.newbooking.BookingView.class));
        tabs.add(createTab(VaadinIcon.USERS, "user management", MemberManagementView.class));

        return tabs;
    }

    @Nonnull
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
}