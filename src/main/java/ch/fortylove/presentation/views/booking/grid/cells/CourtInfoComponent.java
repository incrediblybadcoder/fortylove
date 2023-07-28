package ch.fortylove.presentation.views.booking.grid.cells;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import com.vaadin.flow.component.html.Image;

import javax.annotation.Nonnull;

public class CourtInfoComponent extends CellComponent {

    public CourtInfoComponent(@Nonnull final Court court) {
        constructUI(court);
    }

    private void constructUI(@Nonnull final Court court) {
        final CourtIcon courtIcon = court.getCourtIcon();
        final Image icon = new Image(courtIcon.getResource(), court.getIdentifier());

        add(icon);
    }
}
