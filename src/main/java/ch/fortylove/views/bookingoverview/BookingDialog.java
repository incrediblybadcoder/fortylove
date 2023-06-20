package ch.fortylove.views.bookingoverview;

import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.User;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BookingDialog extends Dialog {

    @Nonnull private final Booking booking;
    @Nonnull private final User bookingPlayer;
    @Nonnull private final List<User> players;

    public BookingDialog(@Nonnull final Booking booking,
                         @Nonnull final User bookingPlayer,
                         @Nonnull final List<User> players) {
        this.booking = booking;
        this.bookingPlayer = bookingPlayer;
        this.players = players;

        constructUI();
    }

    private void constructUI() {
        setHeaderTitle("Buchung, Platz xx, xx:xx, xx.xx.xxxx");

        final VerticalLayout dialogLayout = new VerticalLayout();

        final Span bookingPlayerName = new Span(bookingPlayer.getFirstName());

        final ComboBox<User> userComboBox = new ComboBox<>("Spieler");
        userComboBox.setItems(players);
        userComboBox.setItemLabelGenerator(User::getFirstName);

        final Button saveButton = new Button("Buchen", getSaveButtonClickListener());
        final Button cancelButton = new Button("Abbrechen", e -> close());
        getFooter().add(cancelButton);
        getFooter().add(saveButton);

        dialogLayout.add(bookingPlayerName, userComboBox);
        add(dialogLayout);
    }

    private ComponentEventListener<ClickEvent<Button>> getSaveButtonClickListener() {
        return clickEvent -> {
            fireEvent(new SaveEvent(this, createSaveBooking()));
            close();
        };
    }

    private Booking createSaveBooking() {
        final Booking booking = new Booking();
        List<User> bookingPlayers = new ArrayList<>();
        bookingPlayers.add(bookingPlayer);
        //bookingPlayers.add(userComboBox.getValue());
        bookingPlayers.add(bookingPlayer);
        booking.setUsers(bookingPlayers);
        booking.setTimeslot(this.booking.getTimeslot());
        booking.setCourt(this.booking.getCourt());
        // Die Buchung welche gespeichert werden soll, wird hier erstellt
        // evt mit Binder umsetzten
        return booking;
    }

    public Registration addSaveButtonClickListener(@Nonnull final ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCancelButtonClickListener(@Nonnull final ComponentEventListener<CancelEvent> listener) {
        return addListener(CancelEvent.class, listener);
    }

    public static abstract class BookingDialogEvent extends ComponentEvent<BookingDialog> {
        @Nonnull private final Booking booking;

        protected BookingDialogEvent(@Nonnull final BookingDialog source,
                                     @Nonnull final Booking booking) {
            super(source, false);
            this.booking = booking;
        }

        @Nonnull
        public Booking getBooking() {
            return booking;
        }
    }

    public static class SaveEvent extends BookingDialogEvent {
        SaveEvent(@Nonnull final BookingDialog source,
                  @Nonnull final Booking booking) {
            super(source, booking);
        }
    }

    public static class CancelEvent extends BookingDialogEvent {
        CancelEvent(@Nonnull final BookingDialog source,
                    @Nonnull final Booking booking) {
            super(source, booking);
        }
    }
}