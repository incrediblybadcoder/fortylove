package ch.fortylove.views.newbooking.dialog;

import ch.fortylove.persistence.dto.BookingDTO;
import ch.fortylove.persistence.dto.CourtDTO;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.settings.TimeSlot;
import ch.fortylove.views.newbooking.dialog.events.DialogBookingEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class BookingDialog extends Dialog {

    @Nonnull private final CourtDTO court;
    @Nonnull private final TimeSlot timeSlot;
    @Nonnull private final LocalDate date;
    @Nonnull private final User bookingPlayer;
    @Nonnull private final List<User> players;

    private ComboBox<User> opponentComboBox;
    private Button newButton;
    private Button modifyButton;
    private Button deleteButton;
    private Button cancelButton;

    @Nullable private BookingDTO existingBooking;

    public BookingDialog(@Nonnull final CourtDTO court,
                         @Nonnull final TimeSlot timeSlot,
                         @Nonnull final LocalDate date,
                         @Nonnull final User bookingPlayer,
                         @Nonnull final List<User> players) {
        this.court = court;
        this.timeSlot = timeSlot;
        this.date = date;
        this.bookingPlayer = bookingPlayer;
        this.players = players;

        constructUI();
    }

    private void constructUI() {
        final VerticalLayout dialogLayout = new VerticalLayout();

        final Span bookingPlayerName = new Span(bookingPlayer.getFirstName());

        opponentComboBox = new ComboBox<>("Gegner");
        opponentComboBox.setItems(players);
        opponentComboBox.setItemLabelGenerator(User::getFirstName);

        newButton = new Button("Buchen", newButtonClickListener());
        modifyButton = new Button("Modifizieren", modifyButtonClickListener());
        deleteButton = new Button("Löschen", deleteButtonClickListener());
        cancelButton = new Button("Abbrechen", event -> close());

        dialogLayout.add(bookingPlayerName, opponentComboBox);
        add(dialogLayout);
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> newButtonClickListener() {
        close();
        return event -> fireEvent(DialogBookingEvent.newBooking(this, court, timeSlot, createNewBooking()));
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> modifyButtonClickListener() {
        close();
        return event -> fireEvent(DialogBookingEvent.modifyBooking(this, court, timeSlot, getModifyBooking()));
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> deleteButtonClickListener() {
        close();
        return event -> fireEvent(DialogBookingEvent.deleteBooking(this, court, timeSlot, getDeleteBooking()));
    }

    @Nonnull
    private BookingDTO createNewBooking() {
        final List<User> users = Arrays.asList(bookingPlayer, opponentComboBox.getValue());
        return new BookingDTO(court, users, timeSlot.getIndex(), date);
    }

    @Nonnull
    private BookingDTO getDeleteBooking() {
        if (existingBooking == null) {
            throw new IllegalStateException("Booking dialog in existing mode without existing booking.");
        }
        return existingBooking;
    }

    @Nonnull
    private BookingDTO getModifyBooking() {
        if (existingBooking == null) {
            throw new IllegalStateException("Booking dialog in existing mode without existing booking.");
        }
        return null;
    }

    public void openFree() {
        final String title = "Buchen - Platz " + court.id() + " - " + date + " - " + timeSlot.getStartTime() + " - " + timeSlot.getEndTime();
        setHeaderTitle(title);
        addButtons(Arrays.asList(newButton, cancelButton));

        open();
    }

    public void openExisting(@Nonnull final User opponent,
                             @Nonnull final BookingDTO existingBooking) {
        this.existingBooking = existingBooking;

        final String title = "Modifizieren - Platz " + court.id() + " - " + date + " - " + timeSlot.getStartTime() + " - " + timeSlot.getEndTime();
        setHeaderTitle(title);

        addButtons(Arrays.asList(modifyButton, deleteButton, cancelButton));
        opponentComboBox.setValue(opponent);

        open();
    }

    private void addButtons(@Nonnull final List<Button> buttons) {
        getFooter().removeAll();
        buttons.forEach(this::add);
    }

    public void addDialogBookingListener(@Nonnull final ComponentEventListener<DialogBookingEvent> listener) {
        addListener(DialogBookingEvent.class, listener);
    }
}