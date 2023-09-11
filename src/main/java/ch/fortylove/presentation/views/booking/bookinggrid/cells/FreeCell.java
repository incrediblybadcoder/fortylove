package ch.fortylove.presentation.views.booking.bookinggrid.cells;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class FreeCell extends BookableCell {

    private FreeCell(final boolean isActive,
                    @Nullable final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        super(isActive, clickListener);
    }

    @Nonnull
    public static FreeCell active(@Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        return new FreeCell(true, clickListener);
    }

    @Nonnull
    public static FreeCell inActive() {
        return new FreeCell(false, null);
    }
}
