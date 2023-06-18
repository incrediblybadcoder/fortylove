package ch.fortylove.views.booking.bookinggrid.rows;

import ch.fortylove.persistence.entity.settings.BookingSettings;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.views.booking.bookinggrid.cells.BookingGridCellComponent;
import ch.fortylove.views.booking.bookinggrid.cells.TimeSlotCellComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BookingGridHeaderComponent extends BookingGridRowComponent {


    public BookingGridHeaderComponent(@Nonnull final BookingSettings bookingSettings) {
        super();

        constructUI(bookingSettings);
    }

    private void constructUI(@Nonnull final BookingSettings bookingSettings) {
        setFirstCell(createEmptyCell());

        addCells(createTimeAxisCells(bookingSettings));
    }

    @Nonnull
    private List<BookingGridCellComponent> createTimeAxisCells(@Nonnull final BookingSettings bookingSettings) {
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
