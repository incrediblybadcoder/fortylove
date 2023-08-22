package ch.fortylove.presentation.views.booking.dialog;

import ch.fortylove.configuration.setupdata.StaticConfiguration;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.dialog.CancelableDialog;
import ch.fortylove.presentation.views.booking.dialog.events.DialogBookingEvent;
import ch.fortylove.util.FormatUtil;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class BookingDialog extends CancelableDialog {

    @Nonnull private final Court court;
    @Nonnull private final Timeslot timeslot;
    @Nonnull private final LocalDate date;
    @Nonnull private final User owner;
    @Nonnull private final List<User> possibleOpponents;

    private MultiSelectComboBox<User> opponentComboBox;
    private HorizontalLayout buttonContainer;
    private Button newButton;
    private Button modifyButton;
    private Button deleteButton;

    @Nullable private Booking existingBooking;

    public BookingDialog(@Nonnull final Court court,
                         @Nonnull final Timeslot timeslot,
                         @Nonnull final LocalDate date,
                         @Nonnull final User owner,
                         @Nonnull final List<User> possibleOpponents) {
        this.court = court;
        this.timeslot = timeslot;
        this.date = date;
        this.owner = owner;
        this.possibleOpponents = possibleOpponents;

        constructUI();
    }

    private void constructUI() {
        final VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setSpacing(false);
        dialogLayout.setPadding(false);

        final String fieldWidth = "300px";
        final TextField courtField = new TextField("Platz");
        courtField.setValue(court.getIdentifier());
        courtField.setReadOnly(true);
        courtField.setWidth(fieldWidth);

        final TextField dateField = new TextField("Zeit / Datum");
        dateField.setValue(timeslot.getTimeIntervalText() + " / " + date.format(FormatUtil.getDateTextFormatter()));
        dateField.setReadOnly(true);
        dateField.setWidth(fieldWidth);

        final TextField ownerField = new TextField("Spieler");
        ownerField.setValue(owner.getFullName());
        ownerField.setReadOnly(true);
        ownerField.setWidth(fieldWidth);

        opponentComboBox = new MultiSelectComboBox<>("Gegenspieler");
        opponentComboBox.setItems(possibleOpponents);
        opponentComboBox.setItemLabelGenerator(User::getFullName);
        opponentComboBox.setRequired(true);
        opponentComboBox.setRequiredIndicatorVisible(true);
        opponentComboBox.setWidth(fieldWidth);
        opponentComboBox.addFocusListener(event -> validateOpponentSelection(opponentComboBox.getValue()));
        opponentComboBox.addValueChangeListener(event -> validateOpponentSelection(event.getValue()));
        opponentComboBox.addValueChangeListener(this::restrictMaximumOpponentSelection);
        opponentComboBox.focus();

        newButton = new Button("Speichern", newButtonClickListener());
        newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        modifyButton = new Button("Speichern", modifyButtonClickListener());
        modifyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton = new Button("Löschen", deleteButtonClickListener());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonContainer = new HorizontalLayout();
        buttonContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        getFooter().add(buttonContainer);

        dialogLayout.add(courtField, dateField, ownerField, opponentComboBox);
        add(dialogLayout);
    }

    private void restrictMaximumOpponentSelection(final AbstractField.ComponentValueChangeEvent<MultiSelectComboBox<User>, Set<User>> event) {
        final Set<User> previousSelection = event.getOldValue();
        final Set<User> selection = event.getValue();

        if (selection.size() > StaticConfiguration.MAX_AMOUNT_OF_OPPONENTS_PER_BOOKING) {
            opponentComboBox.setValue(previousSelection);
        }
        opponentComboBox.setOpened(false);
    }

    private void validateOpponentSelection(@Nonnull final Set<User> user) {
        newButton.setEnabled(!user.isEmpty());
        modifyButton.setEnabled(!user.isEmpty());
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> newButtonClickListener() {
        return event -> {
            fireEvent(DialogBookingEvent.newBooking(this, court, timeslot, createNewBooking()));
            close();
        };
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> modifyButtonClickListener() {
        return event -> {
            fireEvent(DialogBookingEvent.modifyBooking(this, court, timeslot, getModifyBooking()));
            close();
        };
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> deleteButtonClickListener() {
        return event -> {
            fireEvent(DialogBookingEvent.deleteBooking(this, court, timeslot, getDeleteBooking()));
            close();
        };
    }

    @Nonnull
    private Booking createNewBooking() {
        return new Booking(court, owner, getOpponents(), timeslot, date);
    }

    @Nonnull
    private Set<User> getOpponents() {
        return opponentComboBox.getValue();
    }

    @Nonnull
    private Booking getDeleteBooking() {
        if (existingBooking == null) {
            throw new IllegalStateException("Booking dialog in existing mode without existing booking.");
        }
        return existingBooking;
    }

    @Nonnull
    private Booking getModifyBooking() {
        if (existingBooking == null) {
            throw new IllegalStateException("Booking dialog in existing mode without existing booking.");
        }
        existingBooking.setOpponents(getOpponents());
        return existingBooking;
    }

    public void openFree() {
        setHeaderTitle("Buchen");
        addButtons(newButton);

        open();
    }

    public void openExisting(@Nonnull final Set<User> opponents,
                             @Nonnull final Booking existingBooking) {
        this.existingBooking = existingBooking;

        final String title = "Bearbeiten";
        setHeaderTitle(title);

        addButtons(deleteButton, modifyButton);
        opponentComboBox.setValue(opponents);

        open();
    }

    private void addButtons(@Nonnull final Button... buttons) {
        buttonContainer.removeAll();
        buttonContainer.add(buttons);
    }

    public void addDialogBookingListener(@Nonnull final ComponentEventListener<DialogBookingEvent> listener) {
        addListener(DialogBookingEvent.class, listener);
    }
}