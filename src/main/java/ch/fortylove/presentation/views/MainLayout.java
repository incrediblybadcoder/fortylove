package ch.fortylove.presentation.views;

import ch.fortylove.FortyloveApplication;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.views.booking.BookingView;
import ch.fortylove.presentation.views.legalnotice.LegalNoticeView;
import ch.fortylove.presentation.views.management.ManagementView;
import ch.fortylove.presentation.views.support.SupportView;
import ch.fortylove.presentation.views.usermenu.SettingsView;
import ch.fortylove.presentation.views.usermenu.UserProfileView;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.service.RoleService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MainLayout extends AppLayout {

    @Nonnull private final AuthenticationService authenticationService;
    @Nonnull private final RoleService roleService;

    private H2 viewTitle;

    public MainLayout(@Nonnull final AuthenticationService authenticationService,
                      @Nonnull final RoleService roleService) {
        this.authenticationService = authenticationService;
        this.roleService = roleService;
        initHeader();
        if (authenticationService.getAuthenticatedUser().isPresent()) {
            createHeader();
            createDrawer();
            setPrimarySection(Section.DRAWER);
        }
    }

    private void initHeader() {
        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
    }

    private void createHeader() {
        final DrawerToggle toggle = new DrawerToggle();

        final HorizontalLayout headerContent = new HorizontalLayout();
        headerContent.setWidthFull();
        headerContent.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerContent.setAlignItems(FlexComponent.Alignment.CENTER);
        headerContent.add(viewTitle, getUserMenu());

        addToNavbar(true, toggle, headerContent);
    }

    @Nonnull
    private Component getUserMenu() {
        final Optional<User> authenticatedUser = authenticationService.getAuthenticatedUser();
        if (authenticatedUser.isPresent()) {
            final User user = authenticatedUser.get();
            final Avatar avatar = new Avatar(user.getFullName());

            final MenuBar userMenu = new MenuBar();
            userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
            userMenu.addClassNames(LumoUtility.Padding.Right.MEDIUM);

            final MenuItem mainMenuItem = userMenu.addItem(avatar);
            final SubMenu userSubMenu = mainMenuItem.getSubMenu();
            addUserSubMenuItem(userSubMenu, VaadinIcon.USER.create(), UserProfileView.PAGE_TITLE, UserProfileView.ROUTE);
            addUserSubMenuItem(userSubMenu, VaadinIcon.COG.create(), SettingsView.PAGE_TITLE, SettingsView.ROUTE);
            userSubMenu.add(new Hr());
            addLogoutItem(userSubMenu);

            return userMenu;
        }

        return new Component() {
        };
    }

    private void addUserSubMenuItem(@Nonnull final SubMenu subMenu,
                                    @Nonnull final Icon icon,
                                    @Nonnull final String title,
                                    @Nonnull final String route) {
        icon.addClassName(LumoUtility.Height.XSMALL);
        icon.addClassName(LumoUtility.Width.XSMALL);
        icon.addClassName(LumoUtility.Margin.Right.XSMALL);

        final MenuItem menuItem = subMenu.addItem(icon, clickEvent -> UI.getCurrent().navigate(route));
        menuItem.add(new Text(title));
        menuItem.setCheckable(false);
    }

    private void addLogoutItem(@Nonnull final SubMenu subMenu) {
        final Icon icon = VaadinIcon.SIGN_OUT.create();
        icon.addClassName(LumoUtility.Height.XSMALL);
        icon.addClassName(LumoUtility.Width.XSMALL);
        icon.addClassName(LumoUtility.Margin.Right.XSMALL);

        final MenuItem menuItem = subMenu.addItem(icon, clickEvent -> authenticationService.logout());
        menuItem.add(new Text("Logout"));
        menuItem.setCheckable(false);
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
        footerNavigation.addItem(new SideNavItem(SupportView.PAGE_TITLE, SupportView.class));
        footerNavigation.addItem(new SideNavItem(LegalNoticeView.PAGE_TITLE, LegalNoticeView.class));

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