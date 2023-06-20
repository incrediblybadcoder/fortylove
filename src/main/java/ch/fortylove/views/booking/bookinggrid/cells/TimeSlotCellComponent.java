package ch.fortylove.views.booking.bookinggrid.cells;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;

public class TimeSlotCellComponent extends BookingGridCellComponent {

    public TimeSlotCellComponent(@Nonnull final String time,
                                 final boolean isVisible) {
        super(isVisible);
        setJustifyContentMode(JustifyContentMode.CENTER);

        constructUI(time);
    }

    private void constructUI(@Nonnull final String time) {
        final Span timeLabel = new Span(time);
        timeLabel.addClassName(LumoUtility.FontWeight.BOLD);
        add(timeLabel);
    }
}
