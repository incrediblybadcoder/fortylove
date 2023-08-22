package ch.fortylove.presentation.views.sitenotice;

import ch.fortylove.presentation.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(value = SiteNoticeView.ROUTE, layout = MainLayout.class)
@PageTitle(SiteNoticeView.PAGE_TITLE)
@PermitAll
public class SiteNoticeView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "sitenotice";
    @Nonnull public static final String PAGE_TITLE = "Impressum";

    public SiteNoticeView(){
    }
}