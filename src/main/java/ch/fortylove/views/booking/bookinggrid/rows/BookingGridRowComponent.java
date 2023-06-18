package ch.fortylove.views.booking.bookinggrid.rows;

import ch.fortylove.views.booking.bookinggrid.cells.BookingGridCellComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class BookingGridRowComponent extends HorizontalLayout {

    public BookingGridRowComponent() {
        addClassNames(
                LumoUtility.Background.CONTRAST,
                LumoUtility.Padding.MEDIUM
        );
        setSpacing(false);
    }

    public void setFirstCell(@Nonnull final BookingGridCellComponent bookingGridCellComponent) {
        addComponentAsFirst(bookingGridCellComponent);
    }

    public void addCell(@Nonnull final BookingGridCellComponent bookingGridCellComponent) {
        add(bookingGridCellComponent);
    }

    public void addCells(@Nonnull final List<BookingGridCellComponent> bookingGridCellComponents) {
        bookingGridCellComponents.forEach(this::add);
    }
}
