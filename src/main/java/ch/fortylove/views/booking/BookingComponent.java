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
        bookingGridComponent.addBookedCellClickListener(this::bookedCellClicked);
        bookingGridComponent.addFreeCellClickListener(this::freeCellClicked);
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

    private void bookedCellClicked(@Nonnull final BookedCellClickEvent event) {
        sessionService.getCurrentUser().ifPresent(currentUser -> {
            if (userService.isUserAllowedToModifyBooking(currentUser, event.getBooking())) {
                final Booking booking = event.getBooking();
                final List<User> possibleOpponents = userService.getPossibleBookingOpponents();
                final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), dateSelectionComponent.getDate(), booking.getOwner(), possibleOpponents);
                bookingDialog.addDialogBookingListener(this::handleDialogBooking);
                bookingDialog.openExisting(booking.getOpponents().get(0), booking);
            }
        });
    }

    private void freeCellClicked(@Nonnull final FreeCellClickEvent event) {
        sessionService.getCurrentUser().ifPresent(currentUser -> {
            final List<User> possibleOpponents = userService.getPossibleBookingOpponents();
            final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), dateSelectionComponent.getDate(), currentUser, possibleOpponents);
            bookingDialog.addDialogBookingListener(this::handleDialogBooking);
            bookingDialog.openFree();
        });
    }

    private void handleDialogBooking(@Nonnull final DialogBookingEvent dialogBookingEvent) {
        switch (dialogBookingEvent.getType()) {
            case NEW -> bookingService.create(dialogBookingEvent.getBooking());
            case MODIFY -> bookingService.update(dialogBookingEvent.getBooking());
            case DELETE -> bookingService.delete(dialogBookingEvent.getBooking().getId());
        }
        refresh();
    }
}
