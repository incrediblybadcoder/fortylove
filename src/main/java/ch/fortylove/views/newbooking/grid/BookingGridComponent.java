package ch.fortylove.views.newbooking.grid;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
import ch.fortylove.views.newbooking.grid.cells.BookedCellComponent;
import ch.fortylove.views.newbooking.grid.cells.BookingCellComponent;
import ch.fortylove.views.newbooking.grid.cells.CourtInfoComponent;
import ch.fortylove.views.newbooking.grid.cells.FreeCellComponent;
import ch.fortylove.views.newbooking.grid.events.BookedCellClickEvent;
import ch.fortylove.views.newbooking.grid.events.FreeCellClickEvent;
import ch.fortylove.views.newbooking.grid.util.CourtUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class BookingGridComponent extends Grid<CourtDTO> {

    public BookingGridComponent(@Nonnull final List<TimeSlotDTO> timeSlots) {
        super(CourtDTO.class, false);

        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        setSelectionMode(SelectionMode.NONE);

        constructGrid(timeSlots);
    }

    private void constructGrid(final List<TimeSlotDTO> timeSlots) {
        addComponentColumn(CourtInfoComponent::new).setFrozen(true);

        timeSlots.forEach(timeSlot ->
                addComponentColumn(court -> createBookingComponent(court, timeSlot))
                        .setHeader(timeSlot.getStartTime().toString())
                        .setVisible(timeSlot.getBookable())
        );
    }

    @Nonnull
    private BookingCellComponent createBookingComponent(@Nonnull final CourtDTO court,
                                                        @Nonnull final TimeSlotDTO timeSlot) {
        final Optional<BookingDTO> booking = CourtUtil.getBookingForTimeSlot(court.getBookings(), timeSlot);

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
