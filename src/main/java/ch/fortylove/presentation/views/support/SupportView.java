package ch.fortylove.presentation.views.support;

import ch.fortylove.presentation.components.managementform.SupportComponent;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.login.LoginView;
import ch.fortylove.security.AuthenticationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(value = SupportView.ROUTE, layout = MainLayout.class)
@PageTitle(SupportView.PAGE_TITLE)
@PermitAll
@AnonymousAllowed
public class SupportView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "support";
    @Nonnull public static final String PAGE_TITLE = "Support";

    public SupportView(@Nonnull final SupportComponent supportComponent,
                       @Nonnull final AuthenticationService authenticationService) {
        add(supportComponent);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);

        if (authenticationService.getAuthenticatedUser().isEmpty()) {
            removeAll();
            add(new H2(PAGE_TITLE), supportComponent, new Button("Zurück zur Login Seite", e -> UI.getCurrent().navigate(LoginView.ROUTE)));
        }
    }
}
