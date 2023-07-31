package ch.fortylove.presentation.views.booking.grid.cells;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class FreeCellComponent extends BookingCellComponent {

    public FreeCellComponent(@Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        constructUI(clickListener);
    }

    private void constructUI(@Nonnull final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener) {
        final Span courtLabel = new Span("free");
        add(courtLabel);

        addClickListener(clickListener);
    }
}
