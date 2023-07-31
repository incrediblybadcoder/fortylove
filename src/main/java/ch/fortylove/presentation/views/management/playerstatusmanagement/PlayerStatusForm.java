package ch.fortylove.presentation.views.management.playerstatusmanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.presentation.components.managementform.ManagementForm;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Nonnull;

@SpringComponent
@UIScope
public class PlayerStatusForm extends ManagementForm<PlayerStatus> {

    private TextField nameField;
    private IntegerField bookingsPerDayField;
    private IntegerField bookableDaysInAdvanceField;

    @Override
    protected void instantiateFields() {
        nameField = new TextField();
        bookingsPerDayField = new IntegerField();
        bookableDaysInAdvanceField = new IntegerField();
    }

    @Nonnull
    @Override
    protected Binder<PlayerStatus> getBinder() {
        final Binder<PlayerStatus> binder = new Binder<>(PlayerStatus.class);

        binder.forField(nameField).withValidator(getNameValidator()).bind(PlayerStatus::getName, PlayerStatus::setName);
        binder.forField(bookingsPerDayField).bind(PlayerStatus::getBookingsPerDay, PlayerStatus::setBookingsPerDay);
        binder.forField(bookableDaysInAdvanceField).bind(PlayerStatus::getBookableDaysInAdvance, PlayerStatus::setBookableDaysInAdvance);

        binder.bindInstanceFields(this);
        return binder;
    }

    @Nonnull
    private Validator<? super String> getNameValidator() {
        return (Validator<String>) (value, context) -> value.isBlank() ? ValidationResult.error("Der Name darf nicht leer sein") : ValidationResult.ok();
    }

    @Nonnull
    @Override
    protected VerticalLayout getContent() {
        return new VerticalLayout(getNameField(), getBookingsPerDayField(), getBookableDaysInAdvanceField());
    }

    @Nonnull
    @Override
    protected PlayerStatus getNewItem() {
        return new PlayerStatus("", 0, 0);
    }

    @Nonnull
    @Override
    protected String getItemIdentifier(@Nonnull final PlayerStatus playerStatus) {
        return playerStatus.getIdentifier();
    }

    @Nonnull
    @Override
    protected String getItemName() {
        return "Status";
    }

    @Nonnull
    @Override
    protected Focusable<TextField> getFocusOnOpen() {
        return nameField;
    }

    @Nonnull
    private TextField getNameField() {
        nameField.setWidthFull();
        nameField.setLabel("Name");
        nameField.setValueChangeMode(ValueChangeMode.EAGER);
        nameField.setRequired(true);
        nameField.setRequiredIndicatorVisible(true);
        return nameField;
    }

    @Nonnull
    private IntegerField getBookingsPerDayField() {
        bookingsPerDayField.setLabel("Buchungen pro Tag");
        bookingsPerDayField.setWidthFull();
        bookingsPerDayField.setMin(0);
        bookingsPerDayField.setStep(1);
        bookingsPerDayField.setStepButtonsVisible(true);
        return bookingsPerDayField;
    }

    @Nonnull
    private IntegerField getBookableDaysInAdvanceField() {
        bookableDaysInAdvanceField.setLabel("Buchbare Tage in die Zukunft");
        bookableDaysInAdvanceField.setWidthFull();
        bookableDaysInAdvanceField.setMin(0);
        bookableDaysInAdvanceField.setStep(1);
        bookableDaysInAdvanceField.setStepButtonsVisible(true);
        return bookableDaysInAdvanceField;
    }
}