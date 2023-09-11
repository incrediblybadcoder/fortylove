package ch.fortylove.presentation.views.support;

import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.presentation.views.login.LoginView;
import ch.fortylove.security.AuthenticationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
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

    @Nonnull private final AuthenticationService authenticationService;

    public SupportView(@Nonnull final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

        addClassName("support-view");

        constructUI();
    }

    private void constructUI() {
        final boolean isUserLoggedIn = authenticationService.getAuthenticatedUser().isPresent();

        final Span description = new Span("Haben Sie Fragen oder Probleme? Wir sind hier, um Ihnen zu helfen!");
        final H3 clubContact = new H3("Clubkontakt");
        final Anchor homepageLink = new Anchor("https://www.tcuntervaz.ch/kontakt-1/", "TC Untervaz - Kontakte");
        homepageLink.setTarget("_blank");
        final H3 systemContact = new H3("Kontakt für technische Probleme");
        final Span supportEmail = new Span("fortylove.untervaz@gmail.com");

        if (isUserLoggedIn) {
            add(description, clubContact, homepageLink, systemContact, supportEmail);
        } else {
            setAlignItems(Alignment.CENTER);
            final H2 pageTitle = new H2(PAGE_TITLE);
            final Button backToLoginButton = new Button("Zurück zur Login Seite", event -> UI.getCurrent().navigate(LoginView.ROUTE));

            add(pageTitle, description, clubContact, homepageLink, systemContact, supportEmail,backToLoginButton);
        }
    }
}
