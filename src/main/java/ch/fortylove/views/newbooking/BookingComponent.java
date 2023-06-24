package ch.fortylove.views.newbooking;

import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.views.newbooking.dateselection.DateSelectionComponent;
import ch.fortylove.views.newbooking.dateselection.events.DateChangeEvent;
import ch.fortylove.views.newbooking.dialog.BookingDialog;
import ch.fortylove.views.newbooking.dialog.events.DialogBookingEvent;
import ch.fortylove.views.newbooking.events.BookingEvent;
import ch.fortylove.views.newbooking.events.GridRefreshEvent;
import ch.fortylove.views.newbooking.grid.BookingGridComponent;
import ch.fortylove.views.newbooking.grid.events.BookedCellClickEvent;
import ch.fortylove.views.newbooking.grid.events.FreeCellClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;
import java.util.List;

public class BookingComponent extends VerticalLayout {

    private BookingGridComponent bookingGridComponent;
    private DateSelectionComponent dateSelectionComponent;

    private List<CourtDTO> courts;
    private List<User> users;

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
        bookingGridComponent = new BookingGridComponent(bookingComponentConfiguration.timeSlots());
        bookingGridComponent.addBookedCellClickListener(this::bookedCellClickedListener);
        bookingGridComponent.addFreeCellClickListener(this::freeCellClickedListener);

        return bookingGridComponent;
    }

    public void refreshComponent(@Nonnull final List<CourtDTO> courts,
                                 @Nonnull final List<User> users) {
        this.courts = courts;
        this.users = users;
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
        final User user = new User();
        user.setFirstName("marco");

        final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), dateSelectionComponent.getDate(), user, users);
        bookingDialog.addDialogBookingListener(this::dialogBooking);
        bookingDialog.openExisting(null, event.getBooking());
    }

    private void freeCellClickedListener(@Nonnull final FreeCellClickEvent event) {
        final User user = new User();
        user.setFirstName("marco");

        final BookingDialog bookingDialog = new BookingDialog(event.getCourt(), event.getTimeSlot(), dateSelectionComponent.getDate(), user, users);
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
