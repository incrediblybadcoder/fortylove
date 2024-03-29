package ch.fortylove.presentation.views.booking.bookinggrid.bookingdialog;

import ch.fortylove.configuration.setupdata.StaticConfiguration;
import ch.fortylove.persistence.entity.Booking;
import ch.fortylove.persistence.entity.Court;
import ch.fortylove.persistence.entity.Timeslot;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.dialog.CancelableDialog;
import ch.fortylove.service.UserService;
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
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BookingDialog extends CancelableDialog {

    @Nonnull private final UserService userService;

    @Nonnull private final MultiSelectComboBox<User> opponentComboBox = new MultiSelectComboBox<>("Gegenspieler");
    @Nonnull private final Button newButton = new Button("Speichern");
    @Nonnull private final Button editButton = new Button("Speichern");
    @Nonnull private final Button deleteButton = new Button("Löschen");

    private HorizontalLayout buttonContainer;
    private TextField courtField;
    private TextField dateField;
    private TextField ownerField;

    private Court currentCourt;
    private Timeslot currentTimeslot;
    private LocalDate currentDate;
    private User currentOwner;

    private Booking existingBooking;

    @Autowired
    public BookingDialog(@Nonnull final UserService userService) {
        this.userService = userService;
        constructUI();
    }

    private void constructUI() {
        setWidth("350px");

        final VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setSpacing(false);
        dialogLayout.setPadding(false);

        courtField = new TextField("Platz");
        courtField.setReadOnly(true);
        courtField.setWidthFull();

        dateField = new TextField("Zeit / Datum");
        dateField.setReadOnly(true);
        dateField.setWidthFull();

        ownerField = new TextField("Spieler");
        ownerField.setReadOnly(true);
        ownerField.setWidthFull();

        opponentComboBox.setItemLabelGenerator(User::getFullName);
        opponentComboBox.setRequired(true);
        opponentComboBox.setRequiredIndicatorVisible(true);
        opponentComboBox.setWidthFull();
        opponentComboBox.addFocusListener(event -> validateOpponentSelection(opponentComboBox.getValue()));
        opponentComboBox.addValueChangeListener(event -> validateOpponentSelection(event.getValue()));
        opponentComboBox.addValueChangeListener(this::restrictMaximumOpponentSelection);
        opponentComboBox.focus();

        newButton.addClickListener(newButtonClickListener());
        newButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton.addClickListener(modifyButtonClickListener());
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addClickListener(deleteButtonClickListener());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttonContainer = new HorizontalLayout();
        buttonContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        getFooter().add(buttonContainer);

        dialogLayout.add(courtField, dateField, ownerField, opponentComboBox);
        add(dialogLayout);
    }

    private void restrictMaximumOpponentSelection(@Nonnull final AbstractField.ComponentValueChangeEvent<MultiSelectComboBox<User>, Set<User>> event) {
        final Set<User> previousSelection = event.getOldValue();
        final Set<User> selection = event.getValue();

        if (selection.size() > StaticConfiguration.MAX_AMOUNT_OF_OPPONENTS_PER_BOOKING) {
            opponentComboBox.setValue(previousSelection);
        }
        opponentComboBox.setOpened(false);
    }

    private void validateOpponentSelection(@Nonnull final Set<User> user) {
        newButton.setEnabled(!user.isEmpty());
        editButton.setEnabled(!user.isEmpty());
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> newButtonClickListener() {
        return event -> {
            fireEvent(BookingDialogEvent.newBooking(this, currentCourt, currentTimeslot, createNewBooking()));
            close();
        };
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> modifyButtonClickListener() {
        return event -> {
            fireEvent(BookingDialogEvent.modifyBooking(this, currentCourt, currentTimeslot, getModifyBooking()));
            close();
        };
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> deleteButtonClickListener() {
        return event -> {
            fireEvent(BookingDialogEvent.deleteBooking(this, currentCourt, currentTimeslot, getDeleteBooking()));
            close();
        };
    }

    @Nonnull
    private Booking createNewBooking() {
        return new Booking(currentCourt, currentOwner, getOpponents(), currentTimeslot, currentDate);
    }

    @Nonnull
    private Set<User> getOpponents() {
        return opponentComboBox.getValue();
    }

    @Nonnull
    private Booking getDeleteBooking() {
        return existingBooking;
    }

    @Nonnull
    private Booking getModifyBooking() {
        existingBooking.setOpponents(getOpponents());
        return existingBooking;
    }

    public void openFree(@Nonnull final Court court,
                         @Nonnull final Timeslot timeslot,
                         @Nonnull final LocalDate date,
                         @Nonnull final User owner) {
        setHeaderTitle("Buchen");
        addButtons(newButton);
        setValues(court, timeslot, date, owner);
        open();
    }

    public void openExisting(@Nonnull final Booking existingBooking) {
        this.existingBooking = existingBooking;

        setHeaderTitle("Bearbeiten");
        addButtons(deleteButton, editButton);
        setValues(existingBooking.getCourt(), existingBooking.getTimeslot(), existingBooking.getDate(), existingBooking.getOwner());
        opponentComboBox.setValue(existingBooking.getOpponents());
        open();
    }

    private void setValues(@Nonnull final Court court,
                           @Nonnull final Timeslot timeslot,
                           @Nonnull final LocalDate date,
                           @Nonnull final User owner) {
        currentCourt = court;
        currentTimeslot = timeslot;
        currentDate = date;
        currentOwner = owner;

        final List<User> possibleOpponents = userService.getPossibleBookingOpponents(owner);
        final List<User> mutablePossibleOpponentsList = new ArrayList<>(possibleOpponents);
        Collections.sort(mutablePossibleOpponentsList);

        courtField.setValue(court.getIdentifier());
        dateField.setValue(timeslot.getTimeIntervalText() + " / " + FormatUtil.format(date));
        ownerField.setValue(owner.getFullName());
        opponentComboBox.setItems(mutablePossibleOpponentsList);
    }

    private void addButtons(@Nonnull final Button... buttons) {
        buttonContainer.removeAll();
        buttonContainer.add(buttons);
    }

    public void addBookingDialogListener(@Nonnull final ComponentEventListener<BookingDialogEvent> listener) {
        addListener(BookingDialogEvent.class, listener);
    }
}