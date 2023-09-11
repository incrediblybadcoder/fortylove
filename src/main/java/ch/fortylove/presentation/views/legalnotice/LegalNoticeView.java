package ch.fortylove.presentation.views.legalnotice;

import ch.fortylove.presentation.components.ButtonFactory;
import ch.fortylove.presentation.views.MainLayout;
import ch.fortylove.security.AuthenticationService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

@Route(value = LegalNoticeView.ROUTE, layout = MainLayout.class)
@PageTitle(LegalNoticeView.PAGE_TITLE)
@AnonymousAllowed
public class LegalNoticeView extends VerticalLayout {

    @Nonnull private final ButtonFactory buttonFactory;

    @Nonnull public static final String ROUTE = "legalnotice";
    @Nonnull public static final String PAGE_TITLE = "Impressum";

    public LegalNoticeView(@Nonnull final AuthenticationService authenticationService,
                           @Nonnull final ButtonFactory buttonFactory) {
        this.buttonFactory = buttonFactory;

        addClassName("legalnotice-view");

        constructUI(authenticationService.getAuthenticatedUser().isPresent());
    }

    private void constructUI(final boolean isUserLoggedIn) {
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

            add(pageTitle, name, street, city, country, email, website, buttonFactory.createBackToLoginButton());
        }
    }
}