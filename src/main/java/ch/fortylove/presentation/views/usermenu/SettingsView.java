package ch.fortylove.presentation.views.usermenu;

import ch.fortylove.presentation.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(value = SettingsView.ROUTE, layout = MainLayout.class)
@PageTitle(SettingsView.PAGE_TITLE)
@PermitAll
public class SettingsView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "settings";
    @Nonnull public static final String PAGE_TITLE = "Settings";

    public SettingsView(){
    }
}