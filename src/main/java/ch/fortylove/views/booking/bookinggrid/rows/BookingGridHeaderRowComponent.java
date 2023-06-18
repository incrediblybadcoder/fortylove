package ch.fortylove.views.booking.bookinggrid.rows;

import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.views.booking.bookinggrid.cells.BookingGridCellComponent;
import ch.fortylove.views.booking.bookinggrid.cells.TimeSlotCellComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BookingGridHeaderRowComponent extends BookingGridRowComponent {

    @Nonnull private final BookingSettings bookingSettings;

    public BookingGridHeaderRowComponent(@Nonnull final BookingSettings bookingSettings) {
        super();
        this.bookingSettings = bookingSettings;

        constructUI();
    }

    private void constructUI() {
        setFirstCell(createEmptyCell());

        addCells(createTimeAxisCells());
    }

    @Nonnull
    private List<BookingGridCellComponent> createTimeAxisCells() {
        final List<BookingGridCellComponent> timeAxisCells = new ArrayList<>();
        final List<TimeSlot> timeSlots = bookingSettings.getTimeSlots();

        timeSlots.forEach(timeSlot -> {
            final boolean isBookable = timeSlot.getBookable();
            final TimeSlotCellComponent timeSlotCellComponent = new TimeSlotCellComponent(timeSlot.getTime().toString(), isBookable);
            if (timeSlotCellComponent.isVisible()) {
                timeAxisCells.add(timeSlotCellComponent);
            }
        });

        return timeAxisCells;
    }

    @Nonnull
    private BookingGridCellComponent createEmptyCell() {
        return new BookingGridCellComponent(true) {
        };
    }
}
