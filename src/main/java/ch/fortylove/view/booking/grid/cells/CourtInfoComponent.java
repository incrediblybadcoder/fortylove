package ch.fortylove.view.booking.grid.cells;

import ch.fortylove.persistence.entity.Court;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;

public class CourtInfoComponent extends VerticalLayout {

    public CourtInfoComponent(@Nonnull final Court court) {
        super();

        constructUI(court);
    }

    private void constructUI(@Nonnull final Court court) {
        final Span courtLabel = new Span("Platz: " + court.getId());
        add(courtLabel);
    }
}
