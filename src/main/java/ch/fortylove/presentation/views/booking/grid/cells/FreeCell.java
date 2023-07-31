package ch.fortylove.presentation.views.booking.grid.cells;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class FreeCell extends BookingCell {

    public FreeCell(@Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        constructUI(clickListener);
    }

    private void constructUI(@Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        addClickListener(clickListener);
    }
}
