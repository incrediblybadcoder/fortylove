package ch.fortylove.presentation.components.managementform;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AnonymousAllowed
@Component
public class SupportComponent extends VerticalLayout {
    public SupportComponent() {

        Paragraph description = new Paragraph("Haben Sie Fragen oder Probleme? Wir sind hier, um Ihnen zu helfen!");
        Div descriptionContainer = new Div(description);
        descriptionContainer.setWidthFull();
        descriptionContainer.getStyle().set("text-align", "center");


        H3 clubContact = new H3("Clubkontakt");
        Anchor homepageLink = new Anchor("https://www.tcuntervaz.ch/kontakt-1/", "TC Untervaz - Kontakte");
        homepageLink.setTarget("_blank");
        Div clubContactContainer = new Div(clubContact);
        clubContactContainer.setWidthFull();
        clubContactContainer.getStyle().set("text-align", "center");

        H3 systemContact = new H3("Kontakt für technische Probleme");
        Div systemContactContainer = new Div(systemContact);
        systemContactContainer.setWidthFull();
        systemContactContainer.getStyle().set("text-align", "center");

        Paragraph supportEmail = new Paragraph("fortylove.untervaz@gmail.com");

        add(descriptionContainer, clubContact, homepageLink, systemContactContainer, supportEmail);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
    }
}
