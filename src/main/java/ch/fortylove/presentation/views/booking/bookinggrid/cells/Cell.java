package ch.fortylove.presentation.views.booking.bookinggrid.cells;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class Cell extends VerticalLayout {

    public Cell() {
        addClassName("booking-grid-cell");
        setSpacing(false);
        setPadding(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
