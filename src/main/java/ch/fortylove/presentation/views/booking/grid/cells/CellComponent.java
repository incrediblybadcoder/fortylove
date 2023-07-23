package ch.fortylove.presentation.views.booking.grid.cells;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class CellComponent extends VerticalLayout {

    public CellComponent() {
        setSpacing(false);
        setPadding(false);

//        setMaxHeight(50, Unit.PIXELS);
    }
}
