package ch.fortylove.presentation.views.booking.bookinggrid.cells;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.CourtIcon;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import jakarta.annotation.Nonnull;

public class CourtInfoCell extends Cell {

    public CourtInfoCell(@Nonnull final Court court) {
        addClassName("booking-grid-cell-court-info");
        constructUI(court);
    }

    private void constructUI(@Nonnull final Court court) {
        final CourtIcon courtIcon = court.getCourtIcon();
        final Image icon = new Image(courtIcon.getResource(), court.getIdentifier());
        icon.setWidth("2.5em");

        final Div text = new Div();
        text.addClassName("booking-grid-cell-court-info-text");
        text.setText(String.valueOf(court.getNumber()));

        add(icon, text);
    }
}
