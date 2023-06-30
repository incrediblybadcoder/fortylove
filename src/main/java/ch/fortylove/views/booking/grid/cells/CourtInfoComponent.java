package ch.fortylove.views.booking.grid.cells;

import ch.fortylove.persistence.dto.CourtDTO;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;

public class CourtInfoComponent extends VerticalLayout {

    public CourtInfoComponent(@Nonnull final CourtDTO courtDTO) {
        super();

        constructUI(courtDTO);
    }

    private void constructUI(@Nonnull final CourtDTO courtDTO) {
        final Span courtLabel = new Span("Platz: " + courtDTO.getId());
        add(courtLabel);
    }
}
