package ch.fortylove.views.newbooking.grid.cells;

import ch.fortylove.persistence.dto.CourtDTO;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;

public class CourtInfoComponent extends VerticalLayout {

    public CourtInfoComponent(@Nonnull final CourtDTO court) {
        super();

        constructUI(court);
    }

    private void constructUI(@Nonnull final CourtDTO court) {
        final Span courtLabel = new Span("Platz: " + court.getId());
        add(courtLabel);
    }
}
