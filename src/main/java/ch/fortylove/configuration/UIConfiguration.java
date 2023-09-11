package ch.fortylove.configuration;

import ch.fortylove.security.AuthenticationService;
import ch.fortylove.util.UserInterfaceUtil;
import com.vaadin.flow.server.UIInitEvent;
import com.vaadin.flow.server.UIInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
public class UIConfiguration implements UIInitListener {

    @Nonnull private final AuthenticationService authenticationService;
    @Nonnull private final UserInterfaceUtil userInterfaceUtil;

    @Autowired
    public UIConfiguration(@Nonnull final AuthenticationService authenticationService,
                           @Nonnull final UserInterfaceUtil userInterfaceUtil) {
        this.authenticationService = authenticationService;
        this.userInterfaceUtil = userInterfaceUtil;
    }

    @Override
    public void uiInit(@Nonnull final UIInitEvent event) {
        authenticationService.getAuthenticatedUser().ifPresent(user -> userInterfaceUtil.setTheme(user.getUserSettings().getTheme()));
    }
}
