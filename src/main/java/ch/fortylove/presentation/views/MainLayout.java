package ch.fortylove.presentation.views;

import ch.fortylove.FortyloveApplication;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.presentation.views.booking.BookingView;
import ch.fortylove.presentation.views.feedback.FeedbackView;
import ch.fortylove.presentation.views.logout.LogoutView;
import ch.fortylove.presentation.views.management.ManagementView;
import ch.fortylove.presentation.views.sitenotice.SiteNoticeView;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.service.RoleService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainLayout extends AppLayout {

    @Nonnull private final AuthenticationService authenticationService;
    @Nonnull private final RoleService roleService;

    private H2 viewTitle;

    public MainLayout(@Nonnull final AuthenticationService authenticationService,
                      @Nonnull final RoleService roleService) {
        this.authenticationService = authenticationService;
        this.roleService = roleService;

        createHeader();
        createDrawer();
        setPrimarySection(Section.DRAWER);
    }

    private void createHeader() {
        final DrawerToggle toggle = new DrawerToggle();

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void createDrawer() {
        final H1 applicationName = new H1(FortyloveApplication.APP_NAME);
        applicationName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        final Header header = new Header(applicationName);

        final Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    @Nonnull
    private SideNav createNavigation() {
        final SideNav navigation = new SideNav();

        // general views
        navigation.addItem(new SideNavItem(BookingView.PAGE_TITLE, BookingView.class, VaadinIcon.CALENDAR.create()));

        // privileged views
        getPrivilegedViews().forEach(navigation::addItem);

        // logout
        navigation.addItem(new SideNavItem(LogoutView.PAGE_TITLE, LogoutView.class, VaadinIcon.SIGN_OUT.create()));

        return navigation;
    }

    @Nonnull
    private List<SideNavItem> getPrivilegedViews() {
        final List<SideNavItem> privilegedViews = new ArrayList<>();

        authenticationService.getAuthenticatedUser().ifPresent(authenticatedUser -> {
            final Set<Role> managementRoles = roleService.getDefaultManagementRoles();
            final Set<Role> userRoles = authenticatedUser.getRoles();

            for (final Role userRole : userRoles) {
                if (managementRoles.contains(userRole)) {
                    privilegedViews.add(new SideNavItem(ManagementView.PAGE_TITLE, ManagementView.class, VaadinIcon.COG.create()));
                    break;
                }
            }
        });

        return privilegedViews;
    }

    @Nonnull
    private Footer createFooter() {
        final SideNav footerNavigation = new SideNav();
        footerNavigation.addItem(new SideNavItem(FeedbackView.PAGE_TITLE, FeedbackView.class));
        footerNavigation.addItem(new SideNavItem(SiteNoticeView.PAGE_TITLE, SiteNoticeView.class));

        final Footer layout = new Footer();
        layout.add(footerNavigation);

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}