package ch.fortylove.views.booking;

import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.dto.UserDTO;
import ch.fortylove.views.booking.dateselection.DateSelectionComponent;
import ch.fortylove.views.booking.dateselection.events.DateChangeEvent;
import ch.fortylove.views.booking.dialog.BookingDialog;
import ch.fortylove.views.booking.dialog.events.DialogBookingEvent;
import ch.fortylove.views.booking.events.BookingEvent;
import ch.fortylove.views.booking.events.GridRefreshEvent;
import ch.fortylove.views.booking.grid.BookingGridComponent;
import ch.fortylove.views.booking.grid.events.BookedCellClickEvent;
import ch.fortylove.views.booking.grid.events.FreeCellClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;
import java.util.List;

public class BookingComponent extends VerticalLayout {

    private BookingGridComponent bookingGridComponent;
    private DateSelectionComponent dateSelectionComponent;

    private List<CourtDTO> courtDTOs;
    private List<UserDTO> userDTOs;

    public BookingComponent( @Nonnull final BookingComponentConfiguration bookingComponentConfiguration) {
        setSpacing(false);
        setPadding(false);
        setHeightFull();

        constructUI(bookingComponentConfiguration);
    }

    private void constructUI(@Nonnull final BookingComponentConfiguration bookingComponentConfiguration) {
        add(createBookingGridComponent(bookingComponentConfiguration));
        add(createDateSelectionComponent());
    }

    @Nonnull
    private DateSelectionComponent createDateSelectionComponent() {
        dateSelectionComponent = new DateSelectionComponent();
        dateSelectionComponent.addDateChangeListener(this::dateChanged);

        return dateSelectionComponent;
    }

    @Nonnull
    private BookingGridComponent createBookingGridComponent(@Nonnull final BookingComponentConfiguration bookingComponentConfiguration) {
        bookingGridComponent = new BookingGridComponent(bookingComponentConfiguration.timeSlotDTOs());
        bookingGridComponent.addBookedCellClickListener(this::bookedCellClickedListener);
        bookingGridComponent.addFreeCellClickListener(this::freeCellClickedListener);

        return bookingGridComponent;
    }

    public void refreshComponent(@Nonnull final List<CourtDTO> courtDTOs,
                                 @Nonnull final List<UserDTO> userDTOs) {
        this.courtDTOs = courtDTOs;
        this.userDTOs = userDTOs;
        refreshGrid();
    }

    private void refreshGrid() {
        if (courtDTOs != null) {
            bookingGridComponent.setItems(courtDTOs);
        }
    }

    private void dateChanged(@Nonnull final DateChangeEvent event) {
        fireEvent(new GridRefreshEvent(this, event.getDate()));
    }

    private void bookedCellClickedListener(@Nonnull final BookedCellClickEvent event) {
        final UserDTO userDTO = new UserDTO(0L, "marco", "solombrino", "email", "password", true, null, null);

        final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), dateSelectionComponent.getDate(), userDTO, userDTOs);
        bookingDialog.addDialogBookingListener(this::dialogBooking);
        bookingDialog.openExisting(null, event.getBooking());
    }

    private void freeCellClickedListener(@Nonnull final FreeCellClickEvent event) {
        final UserDTO userDTO = new UserDTO(0L, "marco", "solombrino", "email", "password", true, null, null);

        final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), dateSelectionComponent.getDate(), userDTO, userDTOs);
        bookingDialog.addDialogBookingListener(this::dialogBooking);
        bookingDialog.openFree();
    }

    private void dialogBooking(@Nonnull final DialogBookingEvent dialogBookingEvent) {
        System.out.println(dialogBookingEvent);
    }

    public void addGridRefreshListener(@Nonnull final ComponentEventListener<GridRefreshEvent> listener) {
        addListener(GridRefreshEvent.class, listener);
    }

    public void addBookingListener(@Nonnull final ComponentEventListener<BookingEvent> listener) {
        addListener(BookingEvent.class, listener);
    }
}
