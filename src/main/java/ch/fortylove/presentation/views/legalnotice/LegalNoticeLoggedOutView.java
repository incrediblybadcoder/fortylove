package ch.fortylove.presentation.views.legalnotice;

import ch.fortylove.presentation.components.LegalNoticeComponent;
import ch.fortylove.presentation.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

@Route(LegalNoticeLoggedOutView.ROUTE)
@PageTitle(LegalNoticeLoggedOutView.PAGE_TITLE)
@AnonymousAllowed
public class LegalNoticeLoggedOutView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "legalnoticeloggedout";
    @Nonnull public static final String PAGE_TITLE = "Impressum";

    public LegalNoticeLoggedOutView(@Nonnull final LegalNoticeComponent legalNoticeComponent) {

        setAlignItems(Alignment.CENTER);
        H1 title = new H1(PAGE_TITLE);


        Button backButton = new Button("Zurück zur Login Seite", e -> UI.getCurrent().navigate(LoginView.ROUTE));

        setHorizontalComponentAlignment(Alignment.CENTER, legalNoticeComponent);

        add(title, legalNoticeComponent, backButton);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
    }
}
