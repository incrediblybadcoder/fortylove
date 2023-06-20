package ch.fortylove.views.booking.bookinggrid.cells;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public abstract class BookingGridCellComponent extends HorizontalLayout {

    private final boolean isVisible;

    public BookingGridCellComponent(final boolean isVisible) {
        this.isVisible = isVisible;

        setAlignItems(Alignment.CENTER);
        addClassName("booking-grid-cell");

        setSpacing(false);
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }
}
