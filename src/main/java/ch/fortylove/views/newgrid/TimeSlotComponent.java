package ch.fortylove.views.newgrid;

import ch.fortylove.views.components.ShortenedLabel;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;

public class TimeSlotComponent extends OverviewCellComponent {

    public TimeSlotComponent(@Nonnull final String time) {
        super();
        addClassNames(
                LumoUtility.Background.SUCCESS
        );
        setAlignItems(Alignment.CENTER);

        constructUI(time);
    }

    private void constructUI(@Nonnull final String time) {
        final ShortenedLabel timeLabel = new ShortenedLabel(time);
        timeLabel.setAlignItems(Alignment.CENTER);
        add(timeLabel);
    }
}
