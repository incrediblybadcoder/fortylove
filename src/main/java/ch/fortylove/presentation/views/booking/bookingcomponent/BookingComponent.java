package ch.fortylove.presentation.views.booking.bookingcomponent;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.presentation.components.dialog.DeleteConfirmationDialog;
import ch.fortylove.presentation.views.booking.bookingcomponent.bookingdialog.BookingDialog;
import ch.fortylove.presentation.views.booking.bookingcomponent.bookingdialog.BookingDialogEvent;
import ch.fortylove.presentation.views.booking.bookingcomponent.dateselection.DateSelection;
import ch.fortylove.presentation.views.booking.bookingcomponent.dateselection.events.DateChangeEvent;
import ch.fortylove.presentation.views.booking.bookingcomponent.grid.BookingGrid;
import ch.fortylove.presentation.views.booking.bookingcomponent.grid.BookingGridConfiguration;
import ch.fortylove.presentation.views.booking.bookingcomponent.grid.events.BookedCellClickEvent;
import ch.fortylove.presentation.views.booking.bookingcomponent.grid.events.FreeCellClickEvent;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.service.BookingService;
import ch.fortylove.service.CourtService;
import ch.fortylove.service.util.DatabaseResult;
import ch.fortylove.service.util.ValidationResult;
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
import java.util.UUID;

@SpringComponent
@UIScope
public class BookingComponent extends VerticalLayout {

    @Nonnull private final BookingService bookingService;
    @Nonnull private final AuthenticationService authenticationService;
    @Nonnull private final CourtService courtService;
    @Nonnull private final NotificationUtil notificationUtil;
    @Nonnull private final BookingDialog bookingDialog;

    @Nonnull private final BookingGrid bookingGrid;
    @Nonnull private final DateSelection dateSelection;

    private VerticalLayout emptyCourtComponent;

    @Autowired
    public BookingComponent(@Nonnull final BookingService bookingService,
                            @Nonnull final AuthenticationService authenticationService,
                            @Nonnull final CourtService courtService,
                            @Nonnull final NotificationUtil notificationUtil,
                            @Nonnull final BookingDialog bookingDialog,
                            @Nonnull final BookingGrid bookingGrid,
                            @Nonnull final DateSelection dateSelectionComponent) {
        this.bookingService = bookingService;
        this.authenticationService = authenticationService;
        this.courtService = courtService;
        this.notificationUtil = notificationUtil;
        this.bookingDialog = bookingDialog;
        this.bookingGrid = bookingGrid;
        this.dateSelection = dateSelectionComponent;

        addClassName("booking-component");
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
        bookingDialog.addDialogBookingListener(this::handleBookingDialogEvent);
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
                bookingDialog.openExisting(event.getCourt(), event.getTimeSlot(), getSelectedDate(), booking.getOwner(), booking.getOpponents(), booking);
            }
        });
    }

    private void freeCellClicked(@Nonnull final FreeCellClickEvent event) {
        authenticationService.getAuthenticatedUser().ifPresent(currentUser -> {
            bookingDialog.openFree(event.getCourt(), event.getTimeSlot(), getSelectedDate(), currentUser);
        });
    }

    private void handleBookingDialogEvent(@Nonnull final BookingDialogEvent bookingDialogEvent) {
        switch (bookingDialogEvent.getType()) {
            case NEW -> newBookingEvent(bookingDialogEvent.getBooking());
            case MODIFY -> modifyBookingEvent(bookingDialogEvent.getBooking());
            case DELETE -> deleteBookingEvent(bookingDialogEvent.getBooking());
        }
    }

    private void newBookingEvent(@Nonnull final Booking booking) {
        final DatabaseResult<Booking> bookingDatabaseResult = bookingService.create(booking);
        notificationUtil.databaseNotification(bookingDatabaseResult);
        refresh();
    }

    private void modifyBookingEvent(final @Nonnull Booking booking) {
        final DatabaseResult<Booking> bookingDatabaseResult = bookingService.update(booking);
        notificationUtil.databaseNotification(bookingDatabaseResult);
        refresh();
    }

    private void deleteBookingEvent(final @Nonnull Booking booking) {
        openConfirmDialog(booking);
    }

    private void openConfirmDialog(@Nonnull final Booking booking) {
        new DeleteConfirmationDialog(
                booking.getIdentifier(),
                "Buchung wirklich Löschen?",
                () -> {
                    final DatabaseResult<UUID> bookingDatabaseResult = bookingService.delete(booking.getId());
                    notificationUtil.databaseNotification(bookingDatabaseResult);
                    refresh();
                }
        ).open();
    }

    @Nonnull
    private LocalDate getSelectedDate() {
        return dateSelection.getDate();
    }
}
