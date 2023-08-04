package ch.fortylove.presentation.views.booking;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.dialog.DeleteConfirmationDialog;
import ch.fortylove.presentation.views.booking.dateselection.DateSelection;
import ch.fortylove.presentation.views.booking.dateselection.events.DateChangeEvent;
import ch.fortylove.presentation.views.booking.dialog.BookingDialog;
import ch.fortylove.presentation.views.booking.dialog.events.DialogBookingEvent;
import ch.fortylove.presentation.views.booking.grid.BookingGrid;
import ch.fortylove.presentation.views.booking.grid.BookingGridConfiguration;
import ch.fortylove.presentation.views.booking.grid.events.BookedCellClickEvent;
import ch.fortylove.presentation.views.booking.grid.events.FreeCellClickEvent;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.service.BookingService;
import ch.fortylove.service.CourtService;
import ch.fortylove.service.UserService;
import ch.fortylove.service.ValidationResult;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@SpringComponent
@UIScope
public class BookingComponent extends VerticalLayout {

    @Nonnull private final BookingService bookingService;
    @Nonnull private final AuthenticationService authenticationService;
    @Nonnull private final CourtService courtService;
    @Nonnull private final UserService userService;

    @Nonnull private final BookingGrid bookingGrid;
    @Nonnull private final DateSelection dateSelection;

    private VerticalLayout emptyCourtComponent;

    @Autowired
    public BookingComponent(@Nonnull final BookingService bookingService,
                            @Nonnull final AuthenticationService authenticationService,
                            @Nonnull final CourtService courtService,
                            @Nonnull final UserService userService,
                            @Nonnull final BookingGrid bookingGrid,
                            @Nonnull final DateSelection dateSelectionComponent) {
        this.bookingService = bookingService;
        this.authenticationService = authenticationService;
        this.courtService = courtService;
        this.userService = userService;
        this.bookingGrid = bookingGrid;
        this.dateSelection = dateSelectionComponent;

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(false);

        constructUI();
    }

    public void refresh() {
        final List<Court> courts = courtService.findAllWithBookingsByDate(getSelectedDate());

        emptyCourtComponent.setVisible(courts.isEmpty());
        bookingGrid.setVisible(!courts.isEmpty());
        dateSelection.setVisible(!courts.isEmpty());

        if (!courts.isEmpty()) {
            final BookingGridConfiguration bookingGridConfiguration = new BookingGridConfiguration(getSelectedDate(), courts);
            bookingGrid.refresh(bookingGridConfiguration);
        }
    }

    private void constructUI() {
        add(getEmptyCourtComponent());
        add(getBookingGridComponent());
        add(getDateSelectionComponent());
    }

    @Nonnull
    private VerticalLayout getEmptyCourtComponent() {
        final Span noCourtsText = new Span("Keine Plätze vorhanden");

        emptyCourtComponent = new VerticalLayout(noCourtsText);
        emptyCourtComponent.addClassNames(LumoUtility.Padding.XLARGE);
        emptyCourtComponent.setAlignItems(Alignment.CENTER);
        emptyCourtComponent.setJustifyContentMode(JustifyContentMode.CENTER);

        return emptyCourtComponent;
    }

    @Nonnull
    private BookingGrid getBookingGridComponent() {
        bookingGrid.addBookedCellClickListener(this::bookedCellClicked);
        bookingGrid.addFreeCellClickListener(this::freeCellClicked);
        return bookingGrid;
    }

    @Nonnull
    private DateSelection getDateSelectionComponent() {
        dateSelection.addDateChangeListener(this::dateChanged);
        return dateSelection;
    }

    private void dateChanged(@Nonnull final DateChangeEvent event) {
        refresh();
    }

    private void bookedCellClicked(@Nonnull final BookedCellClickEvent event) {
        authenticationService.getAuthenticatedUser().ifPresent(currentUser -> {
            if (!currentUser.equals(event.getBooking().getOwner())) {
                return;
            }

            final ValidationResult validationResult = bookingService.isBookingModifiableOnDate(event.getBooking());
            if (validationResult.isSuccessful()) {
                final Booking booking = event.getBooking();
                final List<User> possibleOpponents = userService.getPossibleBookingOpponents(currentUser);
                final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), getSelectedDate(), booking.getOwner(), possibleOpponents);
                bookingDialog.addDialogBookingListener(this::handleDialogBooking);
                bookingDialog.openExisting(booking.getOpponents(), booking);
            }
        });
    }

    private void freeCellClicked(@Nonnull final FreeCellClickEvent event) {
        authenticationService.getAuthenticatedUser().ifPresent(currentUser -> {
            final ValidationResult validationResult = bookingService.isBookingCreatableOnDate(event.getCourt(), event.getTimeSlot(), getSelectedDate());
            if (validationResult.isSuccessful()) {
                final List<User> possibleOpponents = userService.getPossibleBookingOpponents(currentUser);
                final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), getSelectedDate(), currentUser, possibleOpponents);
                bookingDialog.addDialogBookingListener(this::handleDialogBooking);
                bookingDialog.openFree();
            }
        });
    }

    private void handleDialogBooking(@Nonnull final DialogBookingEvent dialogBookingEvent) {
        switch (dialogBookingEvent.getType()) {
            case NEW -> newBookingAction(dialogBookingEvent.getBooking());
            case MODIFY -> modifyBookingAction(dialogBookingEvent.getBooking());
            case DELETE -> deleteBookingAction(dialogBookingEvent.getBooking());
        }
    }

    private void newBookingAction(final @Nonnull Booking booking) {
        final ValidationResult validationResult = bookingService.isUserBookingAllowedOnDate(booking.getOwner(), booking.getDate());
        if (validationResult.isSuccessful()) {
            bookingService.create(booking);
            NotificationUtil.infoNotification(String.format("Buchung %s gespeichert", booking.getIdentifier()));
            refresh();
        } else {
            NotificationUtil.errorNotification(validationResult.getMessage());
        }
    }

    private void modifyBookingAction(final @Nonnull Booking booking) {
        bookingService.update(booking);
        NotificationUtil.infoNotification(String.format("Buchung %s gespeichert", booking.getIdentifier()));
        refresh();
    }

    private void deleteBookingAction(final @Nonnull Booking booking) {
        openConfirmDialog(booking);
    }

    private void openConfirmDialog(@Nonnull final Booking booking) {
        new DeleteConfirmationDialog(
                booking.getIdentifier(),
                "Buchung wirklich Löschen?",
                () -> {
                    bookingService.delete(booking.getId());
                    NotificationUtil.infoNotification(String.format("Buchung %s gelöscht", booking.getIdentifier()));
                    refresh();
                }
        ).open();
    }

    @Nonnull
    private LocalDate getSelectedDate() {
        return dateSelection.getDate();
    }
}
