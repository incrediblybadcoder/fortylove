package ch.fortylove.presentation.views.booking.grid.cells;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class FreeCell extends BookableCell {

    public FreeCell(final boolean isInPast,
                    @Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        super(isInPast, clickListener);
    }
}
