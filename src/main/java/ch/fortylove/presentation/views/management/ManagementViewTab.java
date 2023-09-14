package ch.fortylove.presentation.views.management;

import ch.fortylove.presentation.views.Refreshable;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;

public abstract class ManagementViewTab extends VerticalLayout implements Refreshable {

    @Nonnull private final Icon icon;
    @Nonnull private final String title;

    public ManagementViewTab(@Nonnull final Icon icon,
                             @Nonnull final String title) {
        this.icon = icon;
        this.title = title;
        setSizeFull();
        setPadding(false);
        addClassName(LumoUtility.Padding.Top.MEDIUM);
    }

    @Nonnull
    public Icon getIcon() {
        return icon;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    @Nonnull
    public Tab createTab() {
        return new Tab(getIcon(), new Span(getTitle()));
    }
}
