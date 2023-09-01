package ch.fortylove.presentation.views.login.support;

import ch.fortylove.presentation.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

@Route(SupportView.ROUTE)
@PageTitle(SupportView.PAGE_TITLE)
@AnonymousAllowed
public class SupportView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "support";
    @Nonnull public static final String PAGE_TITLE = "Support";

    public SupportView() {
        H1 title = new H1(PAGE_TITLE);

        Paragraph description = new Paragraph("Haben Sie Fragen oder Probleme? Wir sind hier, um Ihnen zu helfen!");

        H2 clubContact = new H2("Clubkontakt");
        Paragraph email = new Paragraph("info@tc-untervaz.com");

        H2 systemContact = new H2("Kontakt für technische Probleme");
        Paragraph supportEmail = new Paragraph("fortylove.untervaz@gmail.com");

        Button backButton = new Button("Zurück zur Login Seite", e -> {
            UI.getCurrent().navigate(LoginView.ROUTE);
        });

        add(title, description, clubContact, email, systemContact, supportEmail, backButton);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
    }
}
