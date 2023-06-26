package ch.fortylove.views.booking.grid;

import ch.fortylove.persistence.dto.Booking;
import ch.fortylove.persistence.dto.Court;
import ch.fortylove.persistence.dto.TimeSlot;
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

    public BookingGridComponent(@Nonnull final List<TimeSlot> timeSlots) {
        super(Court.class, false);

        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        setSelectionMode(SelectionMode.NONE);

        constructGrid(timeSlots);
    }

    private void constructGrid(final List<TimeSlot> timeSlots) {
        addComponentColumn(CourtInfoComponent::new).setFrozen(true);

        timeSlots.forEach(timeSlot ->
                addComponentColumn(court -> createBookingComponent(court, timeSlot))
                        .setHeader(timeSlot.getStartTime().toString())
                        .setVisible(timeSlot.getBookable())
        );
    }

    @Nonnull
    private BookingCellComponent createBookingComponent(@Nonnull final Court court,
                                                        @Nonnull final TimeSlot timeSlot) {
        final Optional<Booking> booking = CourtUtil.getBookingForTimeSlot(court.getBookings(), timeSlot);

        if (booking.isPresent()) {
            final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener = event -> fireEvent(new BookedCellClickEvent(this, court, timeSlot, booking.get()));
            return new BookedCellComponent(booking.get(), clickListener);
        } else {
            final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener = event -> fireEvent(new FreeCellClickEvent(this, court, timeSlot));
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
