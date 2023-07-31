package ch.fortylove.presentation.views;

import ch.fortylove.FortyloveApplication;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.presentation.views.booking.BookingView;
import ch.fortylove.presentation.views.management.ManagementView;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.service.RoleService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainLayout extends AppLayout {

    @Nonnull private final AuthenticationService authenticationService;
    @Nonnull private final RoleService roleService;

    @Autowired
    public MainLayout(@Nonnull final AuthenticationService authenticationService,
                      @Nonnull final RoleService roleService) {
        this.authenticationService = authenticationService;
        this.roleService = roleService;

        setDrawerOpened(false);

        final H3 appName = new H3(FortyloveApplication.APP_NAME);
        appName.addClassNames(LumoUtility.Margin.Left.MEDIUM, LumoUtility.Margin.Right.MEDIUM);

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
        tabs.add(createTab(VaadinIcon.CALENDAR, BookingView.PAGE_TITLE, BookingView.class));
        tabs.addAll(getPrivilegedTabs());
        tabs.add(getLogoutTab());

        return tabs;
    }

    @Nonnull
    private List<Tab> getPrivilegedTabs() {
        final List<Tab> privilegedTabs = new ArrayList<>();

        authenticationService.getAuthenticatedUser().ifPresent(authenticatedUser -> {
            final List<Role> managementRoles = roleService.getManagementRoles();
            final Set<Role> userRoles = authenticatedUser.getRoles();

            for (final Role userRole : userRoles) {
                if (managementRoles.contains(userRole)) {
                    privilegedTabs.add(createTab(VaadinIcon.COG, ManagementView.PAGE_TITLE, ManagementView.class));
                    break;
                }
            }
        });

        return privilegedTabs;
    }

    @Nonnull
    private Tab getLogoutTab() {
        final Icon logoutIcon = VaadinIcon.SIGN_OUT.create();
        final Label logoutLabel = new Label("Logout");
        final VerticalLayout layout = new VerticalLayout(logoutIcon, logoutLabel);
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.addClickListener(event -> authenticationService.logout());

        final Tab logoutTab = new Tab();
        logoutTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        logoutTab.add(layout);
        return logoutTab;
    }

    @Nonnull
    private Tab createTab(@Nonnull final VaadinIcon icon,
                          @Nonnull final String title,
                          @Nonnull final Class<? extends Component> viewClass) {
        final RouterLink content = populateLink(new RouterLink("", viewClass), icon, title);

        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    @Nonnull
    private <T extends HasComponents> T populateLink(@Nonnull final T link,
                                                     @Nonnull final VaadinIcon icon,
                                                     @Nonnull final String title) {
        link.add(icon.create());
        link.add(title);
        return link;
    }
}