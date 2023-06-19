package ch.fortylove.views.booking.bookinggrid.rows;

import ch.fortylove.views.booking.bookinggrid.cells.BookingGridCellComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import javax.annotation.Nonnull;
import java.util.Collection;

public abstract class BookingGridRowComponent extends HorizontalLayout {

    public BookingGridRowComponent() {
        setSpacing(false);
    }

    public void setFirstCell(@Nonnull final BookingGridCellComponent bookingGridCellComponent) {
        addComponentAsFirst(bookingGridCellComponent);
    }

    public void addCell(@Nonnull final BookingGridCellComponent bookingGridCellComponent) {
        add(bookingGridCellComponent);
    }

    public void addCells(@Nonnull final Collection<BookingGridCellComponent> bookingGridCellComponents) {
        bookingGridCellComponents.forEach(this::add);
    }
}
