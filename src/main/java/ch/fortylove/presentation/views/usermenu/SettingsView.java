package ch.fortylove.presentation.views.usermenu;

import ch.fortylove.presentation.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import jakarta.annotation.Nonnull;
import jakarta.annotation.security.PermitAll;

@Route(value = SettingsView.ROUTE, layout = MainLayout.class)
@PageTitle(SettingsView.PAGE_TITLE)
@PermitAll
public class SettingsView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "settings";
    @Nonnull public static final String PAGE_TITLE = "Einstellungen";

    public SettingsView() {
        final Button toggleButton = new Button("Darkmode umschalten", click -> {
            final ThemeList themeList = UI.getCurrent().getElement().getThemeList();

            if (themeList.contains(Lumo.DARK)) {
                themeList.remove(Lumo.DARK);
            } else {
                themeList.add(Lumo.DARK);
            }
        });

        add(toggleButton);
    }
}