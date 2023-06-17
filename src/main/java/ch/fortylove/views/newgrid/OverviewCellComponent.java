package ch.fortylove.views.newgrid;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public abstract class OverviewCellComponent extends HorizontalLayout {

    public OverviewCellComponent() {
        addClassNames(
                LumoUtility.Background.PRIMARY
        );
        setSpacing(false);
        setHeight("50px");
        setWidth("100px");
    }
}
