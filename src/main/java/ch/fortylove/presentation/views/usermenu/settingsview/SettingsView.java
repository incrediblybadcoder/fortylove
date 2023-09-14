package ch.fortylove.presentation.views.usermenu.settingsview;

import ch.fortylove.persistence.entity.Theme;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.containers.ElevatedPanel;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.booking.BookingView;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.service.UserService;
import ch.fortylove.util.UserInterfaceUtil;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(value = SettingsView.ROUTE, layout = MainLayout.class)
@PageTitle(SettingsView.PAGE_TITLE)
@PermitAll
public class SettingsView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "settings";
    @Nonnull public static final String PAGE_TITLE = "Einstellungen";

    @Nonnull private final AuthenticationService authenticationService;
    @Nonnull private final UserService userService;
    @Nonnull private final UserInterfaceUtil userInterfaceUtil;

    public SettingsView(@Nonnull final AuthenticationService authenticationService,
                        @Nonnull final UserService userService,
                        @Nonnull final UserInterfaceUtil userInterfaceUtil) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userInterfaceUtil = userInterfaceUtil;

        constructUI();
    }

    private void constructUI() {
        authenticationService.getAuthenticatedUser().ifPresent(authenticatedUser -> {

            final Checkbox darkThemeCheckBox = getDarkThemeCheckbox(authenticatedUser);
            final Checkbox showArticlesCheckbox = getShowArticlesCheckbox(authenticatedUser);

            final ElevatedPanel settingsPanel = new ElevatedPanel();
            settingsPanel.add(darkThemeCheckBox, showArticlesCheckbox);

            add(settingsPanel);
        });
    }

    @Nonnull
    private Checkbox getDarkThemeCheckbox(@Nonnull final User authenticatedUser) {
        final Theme currentTheme = authenticatedUser.getUserSettings().getTheme();

        final Checkbox darkThemeCheckbox = new Checkbox("Darkmode", currentTheme.isDarkTheme());
        darkThemeCheckbox.addValueChangeListener(event -> {
            final Theme newTheme = event.getValue() ? Theme.DARK : Theme.LIGHT;
            userInterfaceUtil.setTheme(newTheme);
            userService.findById(authenticatedUser.getId()).ifPresent(user -> {
                user.getUserSettings().setTheme(newTheme);
                userService.update(user);
            });
        });
        return darkThemeCheckbox;
    }

    @Nonnull
    private Checkbox getShowArticlesCheckbox(@Nonnull final User authenticatedUser) {
        final boolean isShowArticles = authenticatedUser.getUserSettings().isShowArticles();

        final Checkbox showArticlesCheckbox = new Checkbox(String.format("Zeige Mitteilungen auf %s-Seite", BookingView.PAGE_TITLE), isShowArticles);
        showArticlesCheckbox.addValueChangeListener(event -> {
            userService.findById(authenticatedUser.getId()).ifPresent(user -> {
                user.getUserSettings().setShowArticles(event.getValue());
                userService.update(user);
            });
        });
        return showArticlesCheckbox;
    }
}