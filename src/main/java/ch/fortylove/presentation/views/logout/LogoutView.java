package ch.fortylove.presentation.views.logout;

import ch.fortylove.security.AuthenticationService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(LogoutView.ROUTE)
@PageTitle(LogoutView.PAGE_TITLE)
@PermitAll
public class LogoutView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "logout";
    @Nonnull public static final String PAGE_TITLE = "Logout";

    public LogoutView(@Nonnull final AuthenticationService authenticationService){
        authenticationService.logout();
    }
}