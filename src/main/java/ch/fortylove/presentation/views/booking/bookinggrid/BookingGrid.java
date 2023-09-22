package ch.fortylove.presentation.views.booking.bookinggrid;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.dialog.DeleteConfirmationDialog;
import ch.fortylove.presentation.views.booking.bookinggrid.bookingdialog.BookingDialog;
import ch.fortylove.presentation.views.booking.bookinggrid.bookingdialog.BookingDialogEvent;
import ch.fortylove.presentation.views.booking.bookinggrid.cells.BookedCell;
import ch.fortylove.presentation.views.booking.bookinggrid.cells.Cell;
import ch.fortylove.presentation.views.booking.bookinggrid.cells.CourtInfoCell;
import ch.fortylove.presentation.views.booking.bookinggrid.cells.FreeCell;
import ch.fortylove.presentation.views.booking.bookinggrid.util.CourtUtil;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.service.BookingService;
import ch.fortylove.service.BookingSettingsService;
import ch.fortylove.service.CourtService;
import ch.fortylove.service.util.DatabaseResult;
import ch.fortylove.service.util.ValidationResult;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@SpringComponent
@UIScope
public class BookingGrid extends Grid<Court> {

    @Nonnull private final CourtService courtService;
    @Nonnull private final BookingSettingsService bookingSettingsService;
    @Nonnull private final BookingService bookingService;
    @Nonnull private final AuthenticationService authenticationService;
    @Nonnull private final BookingDialog bookingDialog;
    @Nonnull private final NotificationUtil notificationUtil;

    private LocalDate date;

    @Autowired
    public BookingGrid(@Nonnull final CourtService courtService,
                       @Nonnull final BookingSettingsService bookingSettingsService,
                       @Nonnull final BookingService bookingService,
                       @Nonnull final AuthenticationService authenticationService,
                       @Nonnull final BookingDialog bookingDialog,
                       @Nonnull final NotificationUtil notificationUtil) {
        super(Court.class, false);
        this.courtService = courtService;
        this.bookingSettingsService = bookingSettingsService;
        this.bookingService = bookingService;
        this.authenticationService = authenticationService;
        this.bookingDialog = bookingDialog;
        this.notificationUtil = notificationUtil;

        addClassName("booking-grid");
        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        setSelectionMode(SelectionMode.NONE);

        constructGrid();
        initBookingDialog();
    }

    private void initBookingDialog() {
        bookingDialog.addBookingDialogListener(this::bookingDialogEvent);
    }

    public void refresh(@Nonnull final LocalDate date) {
        this.date = date;
        final List<Court> courts = courtService.findAllWithBookingsByDate(date);
        setItems(courts);
    }

    private void constructGrid() {
        final Set<Timeslot> timeslots = bookingSettingsService.getBookingSettings().getTimeslots();
        addComponentColumn(CourtInfoCell::new)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true);

        final Optional<User> currentUser = authenticationService.getAuthenticatedUser();

        timeslots.forEach(timeslot ->
                addComponentColumn(court -> currentUser.isPresent() ? createBookingCell(court, timeslot, currentUser.get()) : FreeCell.inActive())
                        .setHeader(timeslot.getStartTime().toString())
                        .setTextAlign(ColumnTextAlign.CENTER)
                        .setVisible(timeslot.getBookable())
        );
    }

    @Nonnull
    private Cell createBookingCell(@Nonnull final Court court,
                                   @Nonnull final Timeslot timeslot,
                                   @Nonnull final User user) {
        final Optional<Booking> bookingOptional = CourtUtil.getBookingForTimeSlot(court.getBookings(), timeslot);
        final boolean bookingExists = bookingOptional.isPresent();
        final boolean isActive = bookingService.isBookingSlotActive(timeslot, date, user, bookingExists ? bookingOptional.get() : null);

        if (bookingExists) {
            final Booking booking = bookingOptional.get();
            if (isActive) {
                final boolean isOwner = user.equals(booking.getOwner());
                return BookedCell.active(isOwner, booking, event -> bookedCellClickEvent(booking));
            } else {
                return BookedCell.inActive(booking);
            }
        } else {
            if (isActive) {
                return FreeCell.active(event -> freeCellClickEvent(court, timeslot));
            } else {
                return FreeCell.inActive();
            }
        }
    }

    private void bookedCellClickEvent(@Nonnull final Booking booking) {
        authenticationService.getAuthenticatedUser().ifPresent(currentUser -> {
            notificationUtil.persistentInformationNotification("bookedCellClickEvent");
            if (!currentUser.equals(booking.getOwner())) {
                notificationUtil.persistentInformationNotification("current user: " + currentUser + "booking user: " + booking.getOwner());
                return;
            }

            final ValidationResult validationResult = bookingService.isBookingModifiableOnDate(booking);
            if (validationResult.isSuccessful()) {
                bookingDialog.openExisting(booking);
            } else {
                notificationUtil.persistentInformationNotification(validationResult.getMessage());
            }
        });
    }

    private void freeCellClickEvent(@Nonnull final Court court,
                                    @Nonnull final Timeslot timeslot) {
        authenticationService.getAuthenticatedUser().ifPresent(currentUser -> bookingDialog.openFree(court, timeslot, date, currentUser));
    }

    private void bookingDialogEvent(@Nonnull final BookingDialogEvent bookingDialogEvent) {
        switch (bookingDialogEvent.getType()) {
            case NEW -> newBookingEvent(bookingDialogEvent.getBooking());
            case EDIT -> modifyBookingEvent(bookingDialogEvent.getBooking());
            case DELETE -> deleteBookingEvent(bookingDialogEvent.getBooking());
        }
    }

    private void newBookingEvent(@Nonnull final Booking booking) {
        final DatabaseResult<Booking> bookingDatabaseResult = bookingService.create(booking);
        notificationUtil.databaseNotification(bookingDatabaseResult);
        refresh(date);
    }

    private void modifyBookingEvent(@Nonnull final Booking booking) {
        final DatabaseResult<Booking> bookingDatabaseResult = bookingService.update(booking);
        notificationUtil.databaseNotification(bookingDatabaseResult);
        refresh(date);
    }

    private void deleteBookingEvent(@Nonnull final Booking booking) {
        openConfirmDialog(booking);
    }

    private void openConfirmDialog(@Nonnull final Booking booking) {
        new DeleteConfirmationDialog(
                booking.getIdentifier(),
                "Buchung wirklich Löschen?",
                () -> {
                    final DatabaseResult<UUID> bookingDatabaseResult = bookingService.delete(booking.getId());
                    notificationUtil.databaseNotification(bookingDatabaseResult);
                    refresh(date);
                }
        ).open();
    }
}
