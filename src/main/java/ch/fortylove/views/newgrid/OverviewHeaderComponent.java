package ch.fortylove.views.newgrid;

import ch.fortylove.persistence.entity.BookingSettings;

import javax.annotation.Nonnull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OverviewHeaderComponent extends OverviewRowComponent {

    @Nonnull private final BookingSettings bookingSettings;

    public OverviewHeaderComponent(@Nonnull final BookingSettings bookingSettings) {
        super();
        this.bookingSettings = bookingSettings;

        constructUI();
    }

    private void constructUI() {
        setFirstCell(createEmptyCell());

        addCells(createTimeAxisCells());
    }

    @Nonnull
    private List<OverviewCellComponent> createTimeAxisCells() {
        final List<OverviewCellComponent> timeAxisCells = new ArrayList<>();

        for (int i = 0; i < bookingSettings.getNumberOfTimeslots(); i++) {
            final String time = createTimeLabel(i);
            final TimeSlotComponent timeSlotComponent = new TimeSlotComponent(time);
            timeAxisCells.add(timeSlotComponent);
        }

        return timeAxisCells;
    }

    @Nonnull
    private String createTimeLabel(final int timeSlotIndex) {
        final LocalTime startHour = bookingSettings.getStartHour();
        final LocalTime timeSlotTime = startHour.plusHours(timeSlotIndex);

        return timeSlotTime.toString();
    }

    @Nonnull
    private OverviewCellComponent createEmptyCell() {
        return new OverviewCellComponent() {
        };
    }
}
