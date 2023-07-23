package ch.fortylove.presentation.views.booking.grid.cells;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtType;
import com.vaadin.flow.component.html.Image;

import javax.annotation.Nonnull;

public class CourtInfoComponent extends CellComponent {

    public CourtInfoComponent(@Nonnull final Court court) {
        constructUI(court);
    }

    private void constructUI(@Nonnull final Court court) {
        final CourtType courtType = court.getCourtType();
        final Image icon = new Image(courtType.getIcon(), court.getIdentifier());

        add(icon);
    }
}
