package ch.fortylove.presentation.views.booking.grid;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.presentation.views.booking.grid.cells.BookedCell;
import ch.fortylove.presentation.views.booking.grid.cells.Cell;
import ch.fortylove.presentation.views.booking.grid.cells.CourtInfoCell;
import ch.fortylove.presentation.views.booking.grid.cells.FreeCell;
import ch.fortylove.presentation.views.booking.grid.events.BookedCellClickEvent;
import ch.fortylove.presentation.views.booking.grid.events.FreeCellClickEvent;
import ch.fortylove.presentation.views.booking.grid.util.CourtUtil;
import ch.fortylove.service.BookingSettingsService;
import ch.fortylove.service.TimeSlotService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@SpringComponent
@UIScope
public class BookingGrid extends Grid<Court> {

    @Nonnull private final BookingSettingsService bookingSettingsService;
    @Nonnull private final TimeSlotService timeSlotService;

    @Nonnull private LocalDate date = LocalDate.now();

    @Autowired
    public BookingGrid(@Nonnull final BookingSettingsService bookingSettingsService,
                       @Nonnull final TimeSlotService timeSlotService) {
        super(Court.class, false);
        this.bookingSettingsService = bookingSettingsService;
        this.timeSlotService = timeSlotService;

        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        setSelectionMode(SelectionMode.NONE);
        setAllRowsVisible(true);

        constructGrid();
    }

    private void constructGrid() {
        final Set<Timeslot> timeslots = bookingSettingsService.getBookingSettings().getTimeslots();
        addComponentColumn(CourtInfoCell::new)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true);

        timeslots.forEach(timeslot ->
                addComponentColumn(court -> createBookingCell(court, timeslot))
                        .setHeader(timeslot.getStartTime().toString())
                        .setTextAlign(ColumnTextAlign.CENTER)
                        .setVisible(timeslot.getBookable())
        );
    }

    @Nonnull
    private Cell createBookingCell(@Nonnull final Court court,
                                   @Nonnull final Timeslot timeslot) {
        final Optional<Booking> booking = CourtUtil.getBookingForTimeSlot(court.getBookings(), timeslot);
        final boolean isInPast = timeSlotService.isInPast(LocalDateTime.now(), date, timeslot);

        if (booking.isPresent()) {
            final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener = event -> fireEvent(new BookedCellClickEvent(this, court, timeslot, booking.get()));
            return new BookedCell(isInPast, booking.get(), clickListener);
        } else {
            final ComponentEventListener<ClickEvent<VerticalLayout>> clickListener = event -> fireEvent(new FreeCellClickEvent(this, court, timeslot));
            return new FreeCell(isInPast, clickListener);
        }
    }

    public void addBookedCellClickListener(@Nonnull final ComponentEventListener<BookedCellClickEvent> listener) {
        addListener(BookedCellClickEvent.class, listener);
    }

    public void addFreeCellClickListener(@Nonnull final ComponentEventListener<FreeCellClickEvent> listener) {
        addListener(FreeCellClickEvent.class, listener);
    }

    public void refresh(@Nonnull final BookingGridConfiguration bookingGridConfiguration) {
        date = bookingGridConfiguration.getDate();
        setItems(bookingGridConfiguration.getCourts());
    }
}
