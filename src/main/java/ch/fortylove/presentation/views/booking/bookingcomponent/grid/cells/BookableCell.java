package ch.fortylove.presentation.views.booking.bookingcomponent.grid.cells;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nullable;

public abstract class BookableCell extends Cell {

    public BookableCell(final boolean isActive,
                        @Nullable final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        if (isActive) {
            addClassName("booking-grid-bookable-cell-active");
            if (clickListener != null) {
                addClickListener(clickListener);
            }
        } else {
            addClassName("booking-grid-bookable-cell-inactive");
        }
    }
}
