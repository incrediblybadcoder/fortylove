package ch.fortylove.views.newbooking.grid.cells;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public abstract class BookingCellComponent extends HorizontalLayout {

    public BookingCellComponent() {
        setAlignItems(Alignment.CENTER);
        setSpacing(false);
    }
}
