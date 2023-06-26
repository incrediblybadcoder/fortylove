package ch.fortylove.views.newbooking.grid.cells;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import javax.annotation.Nonnull;

public class FreeCellComponent extends BookingCellComponent {

    public FreeCellComponent(@Nonnull final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener) {
        super();

        constructUI(clickListener);
    }

    private void constructUI(@Nonnull final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener) {
        final Span courtLabel = new Span("free");
        add(courtLabel);

        addClickListener(clickListener);
    }
}
