package ch.fortylove.presentation.views.booking.grid.cells;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import com.vaadin.flow.component.html.Image;
import jakarta.annotation.Nonnull;

public class CourtInfoCell extends BookingCell {

    public CourtInfoCell(@Nonnull final Court court) {
        constructUI(court);
    }

    private void constructUI(@Nonnull final Court court) {
        final CourtIcon courtIcon = court.getCourtIcon();
        final Image icon = new Image(courtIcon.getResource(), court.getIdentifier());
        icon.setWidth("2em");

        add(icon);
    }
}
