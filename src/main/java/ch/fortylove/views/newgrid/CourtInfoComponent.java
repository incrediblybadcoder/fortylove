package ch.fortylove.views.newgrid;

import ch.fortylove.persistence.entity.Court;
import ch.fortylove.views.components.ShortenedLabel;

import javax.annotation.Nonnull;

public class CourtInfoComponent extends OverviewCellComponent {

    public CourtInfoComponent(@Nonnull final Court court) {
        super();
        setAlignItems(Alignment.CENTER);

        constructUI(court);
    }

    private void constructUI(@Nonnull final Court court) {
        final ShortenedLabel courtLabel = new ShortenedLabel("Platz: " + court.getId());
        courtLabel.setAlignItems(Alignment.CENTER);
        add(courtLabel);
    }
}
