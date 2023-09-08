package ch.fortylove.presentation.views.legalnotice;

import ch.fortylove.presentation.components.LegalNoticeComponent;
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

@Route(value = LegalNoticeView.ROUTE, layout = MainLayout.class)
@PageTitle(LegalNoticeView.PAGE_TITLE)
@PermitAll
@AnonymousAllowed
public class LegalNoticeView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "legalnotice";
    @Nonnull public static final String PAGE_TITLE = "Impressum";

    public LegalNoticeView(@Nonnull final LegalNoticeComponent legalNoticeComponent, @Nonnull final AuthenticationService authenticationService) {
        add(legalNoticeComponent);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);


        if (authenticationService.getAuthenticatedUser().isEmpty()) {
            removeAll();
            add(new H2(PAGE_TITLE), legalNoticeComponent, new Button("Zurück zur Login Seite", e -> UI.getCurrent().navigate(LoginView.ROUTE)));
        }
    }
}