package ch.fortylove.views.booking;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.service.BookingService;
import ch.fortylove.persistence.service.CourtService;
import ch.fortylove.persistence.service.SessionService;
import ch.fortylove.persistence.service.UserService;
import ch.fortylove.views.booking.dateselection.DateSelectionComponent;
import ch.fortylove.views.booking.dateselection.events.DateChangeEvent;
import ch.fortylove.views.booking.dialog.BookingDialog;
import ch.fortylove.views.booking.dialog.events.DialogBookingEvent;
import ch.fortylove.views.booking.grid.BookingGridComponent;
import ch.fortylove.views.booking.grid.events.BookedCellClickEvent;
import ch.fortylove.views.booking.grid.events.FreeCellClickEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.List;

@SpringComponent
@UIScope
public class BookingComponent extends VerticalLayout {

    @Nonnull private final BookingService bookingService;
    @Nonnull private final SessionService sessionService;
    @Nonnull private final CourtService courtService;
    @Nonnull private final UserService userService;
    @Nonnull private final BookingGridComponent bookingGridComponent;
    @Nonnull private final DateSelectionComponent dateSelectionComponent;

    @Autowired
    public BookingComponent(@Nonnull final BookingService bookingService,
                            @Nonnull final SessionService sessionService,
                            @Nonnull final CourtService courtService,
                            @Nonnull final UserService userService,
                            @Nonnull final BookingGridComponent bookingGridComponent,
                            @Nonnull final DateSelectionComponent dateSelectionComponent) {
        this.bookingService = bookingService;
        this.sessionService = sessionService;
        this.courtService = courtService;
        this.userService = userService;
        this.bookingGridComponent = bookingGridComponent;
        this.dateSelectionComponent = dateSelectionComponent;

        setSpacing(false);
        setPadding(false);
        setHeightFull();

        constructUI();
    }

    private void constructUI() {
        add(getBookingGridComponent());
        add(getDateSelectionComponent());
    }

    @Nonnull
    private BookingGridComponent getBookingGridComponent() {
        bookingGridComponent.addBookedCellClickListener(this::bookedCellClickedListener);
        bookingGridComponent.addFreeCellClickListener(this::freeCellClickedListener);
        return bookingGridComponent;
    }

    @Nonnull
    private DateSelectionComponent getDateSelectionComponent() {
        dateSelectionComponent.addDateChangeListener(this::dateChanged);
        return dateSelectionComponent;
    }

    public void refresh() {
        final List<Court> courts = courtService.findAllWithBookingsByDate(dateSelectionComponent.getDate());
        bookingGridComponent.setItems(courts);
    }

    private void dateChanged(@Nonnull final DateChangeEvent event) {
        refresh();
    }

    private void bookedCellClickedListener(@Nonnull final BookedCellClickEvent event) {
        sessionService.getCurrentUser().ifPresent(currentUser -> {
            final Booking booking = event.getBooking();
            final List<User> possibleOpponents = userService.findAll();
            final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), dateSelectionComponent.getDate(), booking.getOwner(), possibleOpponents);
            bookingDialog.addDialogBookingListener(this::dialogBooking);
            bookingDialog.openExisting(booking.getOpponents().get(0), booking);
        });
    }

    private void freeCellClickedListener(@Nonnull final FreeCellClickEvent event) {
        sessionService.getCurrentUser().ifPresent(currentUser -> {
            final List<User> possibleOpponents = userService.findAll();
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
        refresh();
    }
}
