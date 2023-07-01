package ch.fortylove.views.booking;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.BookingService;
import ch.fortylove.persistence.service.SessionService;
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

    @Nonnull private final BookingService bookingService;
    @Nonnull private final SessionService sessionService;

    private BookingGridComponent bookingGridComponent;
    private DateSelectionComponent dateSelectionComponent;

    private List<Court> courts;
    private List<User> possibleOpponents;

    public BookingComponent(@Nonnull final BookingService bookingService,
                            @Nonnull final SessionService sessionService,
                            @Nonnull final BookingComponentConfiguration bookingComponentConfiguration) {
        this.bookingService = bookingService;
        this.sessionService = sessionService;
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
        bookingGridComponent = new BookingGridComponent(bookingComponentConfiguration.timeslots());
        bookingGridComponent.addBookedCellClickListener(this::bookedCellClickedListener);
        bookingGridComponent.addFreeCellClickListener(this::freeCellClickedListener);

        return bookingGridComponent;
    }

    public void refreshComponent(@Nonnull final List<Court> courts,
                                 @Nonnull final List<User> possibleOpponents) {
        this.courts = courts;
        this.possibleOpponents = possibleOpponents;
        refreshGrid();
    }

    private void refreshGrid() {
        if (courts != null) {
            bookingGridComponent.setItems(courts);
        }
    }

    private void dateChanged(@Nonnull final DateChangeEvent event) {
        fireEvent(new GridRefreshEvent(this, event.getDate()));
    }

    private void bookedCellClickedListener(@Nonnull final BookedCellClickEvent event) {
        sessionService.getCurrentUser().ifPresent(currentUser -> {
            final Booking booking = event.getBooking();
            final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), dateSelectionComponent.getDate(), booking.getOwner(), possibleOpponents);
            bookingDialog.addDialogBookingListener(this::dialogBooking);
            bookingDialog.openExisting(booking.getOpponents().get(0), booking);
        });
    }

    private void freeCellClickedListener(@Nonnull final FreeCellClickEvent event) {
        sessionService.getCurrentUser().ifPresent(currentUser -> {
            final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), dateSelectionComponent.getDate(), currentUser, possibleOpponents);
            bookingDialog.addDialogBookingListener(this::dialogBooking);
            bookingDialog.openFree();
        });
    }

    private void dialogBooking(@Nonnull final DialogBookingEvent dialogBookingEvent) {
        switch (dialogBookingEvent.getType()) {
            case NEW -> bookingService.create(dialogBookingEvent.getBooking());
            case MODIFY -> bookingService.update(dialogBookingEvent.getBooking());
            case DELETE -> bookingService.delete(dialogBookingEvent.getBooking());
        }

        fireEvent(new GridRefreshEvent(this, dateSelectionComponent.getDate()));
    }

    public void addGridRefreshListener(@Nonnull final ComponentEventListener<GridRefreshEvent> listener) {
        addListener(GridRefreshEvent.class, listener);
    }

    public void addBookingListener(@Nonnull final ComponentEventListener<BookingEvent> listener) {
        addListener(BookingEvent.class, listener);
    }
}
