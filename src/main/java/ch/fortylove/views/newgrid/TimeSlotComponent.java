package ch.fortylove.views.newgrid;

import ch.fortylove.views.components.ShortenedLabel;

import javax.annotation.Nonnull;

public class TimeSlotComponent extends OverviewCellComponent {

    public TimeSlotComponent(@Nonnull final String time) {
        super();
        setAlignItems(Alignment.CENTER);

        constructUI(time);
    }

    private void constructUI(@Nonnull final String time) {
        final ShortenedLabel courtLabel = new ShortenedLabel(time);
        add(courtLabel);
    }
}
