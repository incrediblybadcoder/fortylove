package ch.fortylove.presentation.views.booking.grid.cells;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public abstract class BookableCell extends Cell {

    public BookableCell(final boolean isInPast,
                        @Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        if (isInPast) {
            addClassName("booking-grid-bookable-cell-in-past");
        }

        addClickListener(clickListener);
    }
}
