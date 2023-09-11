package ch.fortylove.util;

import ch.fortylove.persistence.entity.Theme;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.Lumo;
import jakarta.annotation.Nonnull;

@SpringComponent
public class UserInterfaceUtil {

    public void setTheme(@Nonnull final Theme theme) {
        final ThemeList themeList = UI.getCurrent().getElement().getThemeList();

        switch (theme) {
            case LIGHT -> themeList.remove(Lumo.DARK);
            case DARK -> themeList.add(Lumo.DARK);
        }
    }
}
