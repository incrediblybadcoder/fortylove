package ch.fortylove.views.newgrid.cells;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public abstract class OverviewCellComponent extends HorizontalLayout {

    private final boolean isVisible;

    public OverviewCellComponent(final boolean isVisible) {
        this.isVisible = isVisible;

        addClassNames(
                LumoUtility.Border.ALL
        );

        setSpacing(false);
        setHeight("50px");
        setWidth("100px");
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }
}
