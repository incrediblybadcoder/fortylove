package ch.fortylove.presentation.views.legalnotice;

import ch.fortylove.presentation.components.LegalNoticeComponent;
import ch.fortylove.presentation.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(value = LegalNoticeLoggedInView.ROUTE, layout = MainLayout.class)
@PageTitle(LegalNoticeLoggedInView.PAGE_TITLE)
@PermitAll
public class LegalNoticeLoggedInView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "legalnoticeloggedin";
    @Nonnull public static final String PAGE_TITLE = "Impressum";

    public LegalNoticeLoggedInView(@Nonnull final LegalNoticeComponent legalNoticeComponent) {
        add(legalNoticeComponent);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
    }
}