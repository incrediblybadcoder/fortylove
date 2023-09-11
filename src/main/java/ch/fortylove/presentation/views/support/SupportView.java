package ch.fortylove.presentation.views.support;

import ch.fortylove.presentation.components.ButtonFactory;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.security.AuthenticationService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

@Route(value = SupportView.ROUTE, layout = MainLayout.class)
@PageTitle(SupportView.PAGE_TITLE)
@AnonymousAllowed
public class SupportView extends VerticalLayout {

    @Nonnull private final ButtonFactory buttonFactory;

    @Nonnull public static final String ROUTE = "support";
    @Nonnull public static final String PAGE_TITLE = "Support";

    public SupportView(@Nonnull final AuthenticationService authenticationService,
                       @Nonnull final ButtonFactory buttonFactory) {
        this.buttonFactory = buttonFactory;
        addClassName("support-view");
        setSizeFull();

        constructUI(authenticationService.getAuthenticatedUser().isPresent());
    }

    private void constructUI(final boolean isUserLoggedIn) {
        final Span description = new Span("Haben Sie Fragen oder Probleme? Wir sind hier, um Ihnen zu helfen!");
        description.addClassName("center-text");
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

            add(pageTitle, description, clubContact, homepageLink, systemContact, supportEmail, buttonFactory.createBackToLoginButton());
        }
    }
}
