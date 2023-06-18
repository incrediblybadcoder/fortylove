package ch.fortylove.views.booking.bookinggrid.cells;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public abstract class BookingGridCellComponent extends HorizontalLayout {

    private final boolean isVisible;

    public BookingGridCellComponent(final boolean isVisible) {
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
