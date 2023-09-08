package ch.fortylove.presentation.views.legalnotice;

import ch.fortylove.presentation.components.LegalNoticeComponent;
import ch.fortylove.presentation.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(value = LegalNoticeView.ROUTE, layout = MainLayout.class)
@PageTitle(LegalNoticeView.PAGE_TITLE)
@PermitAll
public class LegalNoticeView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "legalnotice";
    @Nonnull public static final String PAGE_TITLE = "Impressum";

    public LegalNoticeView(@Nonnull final LegalNoticeComponent legalNoticeComponent) {
        add(legalNoticeComponent);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
    }
}