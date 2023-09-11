package ch.fortylove.presentation.views.usermenu;

import ch.fortylove.persistence.entity.Theme;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.service.UserService;
import ch.fortylove.util.UserInterfaceUtil;
import com.vaadin.flow.component.button.Button;
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
        authenticationService.getAuthenticatedUser().ifPresent(user -> {
            final Button toggleButton = new Button("Darkmode umschalten", click -> {
                final Theme newTheme = user.getUserSettings().getTheme().equals(Theme.LIGHT) ? Theme.DARK : Theme.LIGHT;
                userInterfaceUtil.setTheme(newTheme);
                user.getUserSettings().setTheme(newTheme);
                userService.update(user);
            });
            add(toggleButton);
        });
    }
}