package ch.fortylove.views;

import ch.fortylove.security.SecurityService;
import ch.fortylove.views.membermanagement.MemberManagementView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;



@CssImport(value = "./themes/fortylove/views/main-layout.css", themeFor = "vaadin-vertical-layout")
public class MainLayout extends AppLayout {

    @Nonnull private final SecurityService securityService;

    public MainLayout(@Nonnull final SecurityService securityService) {
        this.securityService = securityService;

        setDrawerOpened(false);

        final H3 appName = new H3("fortylove");
        appName.addClassNames(LumoUtility.Margin.Left.MEDIUM, LumoUtility.Margin.Right.MEDIUM);
        appName.getStyle().set("left", "var(--lumo-space-l)");

        final Tabs menu = createMenuTabs();

        addToNavbar(appName, menu);
    }

    @Nonnull
    private Button createLogoutButton() {
        final Button logoutButton = new Button("Logout", new Icon(VaadinIcon.SIGN_OUT));
        logoutButton.getStyle().set("margin-right", "var(--lumo-space-m)");
        logoutButton.addClickListener(event -> {
            securityService.logout();
        });
        return logoutButton;
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
        tabs.add(createTab(VaadinIcon.CALENDAR, "Buchen", ch.fortylove.views.booking.BookingView.class));
        tabs.add(createTab(VaadinIcon.USERS, "Benutzerverwaltung", MemberManagementView.class));

        final Icon logoutIcon = new Icon(VaadinIcon.SIGN_OUT);
        final Span logoutText = new Span("Logout");
        logoutText.addClassName("logout-text");  // Fügen Sie die CSS-Klasse hinzu

        final VerticalLayout logoutLink = new VerticalLayout(logoutIcon, logoutText);
        logoutLink.getElement().getThemeList().add("logout-link");  // Fügen Sie den Theme-Namen hinzu
        logoutLink.getStyle().set("cursor", "pointer");
        logoutLink.setPadding(false);
        logoutLink.setSpacing(false);
        logoutLink.addClickListener(event -> {
            securityService.logout();
        });

        final Tab logoutTab = new Tab();
        logoutTab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        logoutTab.add(logoutLink);
        tabs.add(logoutTab);

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