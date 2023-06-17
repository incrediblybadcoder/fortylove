package ch.fortylove.views.newgrid;

import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.entity.settings.TimeSlot;

import javax.annotation.Nonnull;
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
        final List<TimeSlot> timeSlots = bookingSettings.getTimeSlots();

        timeSlots.forEach(timeSlot -> {
            final TimeSlotComponent timeSlotComponent = new TimeSlotComponent(timeSlot.getTime().toString());
            timeAxisCells.add(timeSlotComponent);

        });

        return timeAxisCells;
    }

    @Nonnull
    private OverviewCellComponent createEmptyCell() {
        return new OverviewCellComponent() {
        };
    }
}
