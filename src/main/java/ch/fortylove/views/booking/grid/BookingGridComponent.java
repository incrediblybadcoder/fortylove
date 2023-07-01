package ch.fortylove.views.booking.grid;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.views.booking.grid.cells.BookedCellComponent;
import ch.fortylove.views.booking.grid.cells.BookingCellComponent;
import ch.fortylove.views.booking.grid.cells.CourtInfoComponent;
import ch.fortylove.views.booking.grid.cells.FreeCellComponent;
import ch.fortylove.views.booking.grid.events.BookedCellClickEvent;
import ch.fortylove.views.booking.grid.events.FreeCellClickEvent;
import ch.fortylove.views.booking.grid.util.CourtUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class BookingGridComponent extends Grid<Court> {

    public BookingGridComponent(@Nonnull final List<Timeslot> timeslots) {
        super(Court.class, false);

        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        setSelectionMode(SelectionMode.NONE);

        constructGrid(timeslots);
    }

    private void constructGrid(final List<Timeslot> timeslots) {
        addComponentColumn(CourtInfoComponent::new).setFrozen(true);

        timeslots.forEach(timeslot ->
                addComponentColumn(court -> createBookingComponent(court, timeslot))
                        .setHeader(timeslot.getStartTime().toString())
                        .setVisible(timeslot.getBookable())
        );
    }

    @Nonnull
    private BookingCellComponent createBookingComponent(@Nonnull final Court court,
                                                        @Nonnull final Timeslot timeslot) {
        final Optional<Booking> booking = CourtUtil.getBookingForTimeSlot(court.getBookings(), timeslot);

        if (booking.isPresent()) {
            final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener = event -> fireEvent(new BookedCellClickEvent(this, court, timeslot, booking.get()));
            return new BookedCellComponent(booking.get(), clickListener);
        } else {
            final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener = event -> fireEvent(new FreeCellClickEvent(this, court, timeslot));
            return new FreeCellComponent(clickListener);
        }
    }

    public void addBookedCellClickListener(@Nonnull final ComponentEventListener<BookedCellClickEvent> listener) {
        addListener(BookedCellClickEvent.class, listener);
    }

    public void addFreeCellClickListener(@Nonnull final ComponentEventListener<FreeCellClickEvent> listener) {
        addListener(FreeCellClickEvent.class, listener);
    }
}
