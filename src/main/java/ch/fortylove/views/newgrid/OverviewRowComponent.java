package ch.fortylove.views.newgrid;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class OverviewRowComponent extends HorizontalLayout {

    public OverviewRowComponent() {
        addClassNames(
                LumoUtility.Background.CONTRAST,
                LumoUtility.Padding.MEDIUM
        );
        setSpacing(false);
    }

    public void setFirstCell(@Nonnull final OverviewCellComponent overviewCellComponent) {
        addComponentAsFirst(overviewCellComponent);
    }

    public void addCell(@Nonnull final OverviewCellComponent overviewCellComponent) {
        add(overviewCellComponent);
    }

    public void addCells(@Nonnull final List<OverviewCellComponent> overviewCellComponents) {
        overviewCellComponents.forEach(this::add);
    }
}
