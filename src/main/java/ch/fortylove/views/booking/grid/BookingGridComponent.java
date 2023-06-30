package ch.fortylove.views.booking.grid;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.TimeSlotDTO;
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

public class BookingGridComponent extends Grid<CourtDTO> {

    public BookingGridComponent(@Nonnull final List<TimeSlotDTO> timeSlotDTOs) {
        super(CourtDTO.class, false);

        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        setSelectionMode(SelectionMode.NONE);

        constructGrid(timeSlotDTOs);
    }

    private void constructGrid(final List<TimeSlotDTO> timeSlotDTOs) {
        addComponentColumn(CourtInfoComponent::new).setFrozen(true);

        timeSlotDTOs.forEach(timeSlot ->
                addComponentColumn(court -> createBookingComponent(court, timeSlot))
                        .setHeader(timeSlot.getStartTime().toString())
                        .setVisible(timeSlot.getBookable())
        );
    }

    @Nonnull
    private BookingCellComponent createBookingComponent(@Nonnull final CourtDTO courtDTO,
                                                        @Nonnull final TimeSlotDTO timeSlotDTO) {
        final Optional<BookingDTO> booking = CourtUtil.getBookingForTimeSlot(courtDTO.getBookings(), timeSlotDTO);

        if (booking.isPresent()) {
            final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener = event -> fireEvent(new BookedCellClickEvent(this, courtDTO, timeSlotDTO, booking.get()));
            return new BookedCellComponent(booking.get(), clickListener);
        } else {
            final ComponentEventListener<ClickEvent<HorizontalLayout>> clickListener = event -> fireEvent(new FreeCellClickEvent(this, courtDTO, timeSlotDTO));
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
