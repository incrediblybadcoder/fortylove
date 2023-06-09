package ch.fortylove.views.bookingoverview;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.utility.CourtUtility;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import javax.annotation.Nonnull;
import java.util.List;

public class BookingGrid extends Grid<Court> {

    private static final int AMOUNT_OF_BOOKINGS = 16;

    public BookingGrid() {
        setSizeFull();
        setSelectionMode(Grid.SelectionMode.NONE);
        addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);

        constructUI();
    }

    private void constructUI() {
        addComponentColumn(this::createCourt);
    }

    private HorizontalLayout createCourt(@Nonnull final Court court) {
        final HorizontalLayout courtContainer = new HorizontalLayout();
        courtContainer.setSpacing(false);

        final List<Booking> preparedBookings = CourtUtility.prepareBookings(AMOUNT_OF_BOOKINGS, court);
        preparedBookings.forEach(booking -> courtContainer.add(new BookingComponent(booking)));

        return courtContainer;
    }
}
