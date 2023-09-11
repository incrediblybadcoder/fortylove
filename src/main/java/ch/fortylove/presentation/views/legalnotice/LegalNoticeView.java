package ch.fortylove.presentation.views.legalnotice;

import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.login.LoginView;
import ch.fortylove.security.AuthenticationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(value = LegalNoticeView.ROUTE, layout = MainLayout.class)
@PageTitle(LegalNoticeView.PAGE_TITLE)
@PermitAll
@AnonymousAllowed
public class LegalNoticeView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "legalnotice";
    @Nonnull public static final String PAGE_TITLE = "Impressum";

    @Nonnull private final AuthenticationService authenticationService;

    public LegalNoticeView(@Nonnull final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

        addClassName("legalnotice-view");

        constructUI();
    }

    private void constructUI() {
        final boolean isUserLoggedIn = authenticationService.getAuthenticatedUser().isPresent();

        final Span name = new Span("Jonas Cahenzli");
        final Span street = new Span("Hünenbergstrasse 19");
        final Span city = new Span("6006 Luzern");
        final Span country = new Span("Schweiz");
        final Span email = new Span("jonas.cahenzli@gmail.com");
        final Span website = new Span("www.fortylove.ch seit 2023");

        if (isUserLoggedIn) {
            add(name, street, city, country, email, website);
        } else {
            setAlignItems(Alignment.CENTER);
            final H2 pageTitle = new H2(PAGE_TITLE);
            final Button backToLoginButton = new Button("Zurück zur Login Seite", event -> UI.getCurrent().navigate(LoginView.ROUTE));

            add(pageTitle, name, street, city, country, email, website, backToLoginButton);
        }
    }
}