package ch.fortylove.presentation.views.management.playerstatusmanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.presentation.components.ButtonFactory;
import ch.fortylove.presentation.components.InputFieldFactory;
import ch.fortylove.presentation.components.dialog.CancelableDialog;
import ch.fortylove.presentation.components.dialog.Dialog;
import ch.fortylove.presentation.components.managementform.ManagementForm;
import ch.fortylove.service.PlayerStatusService;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PlayerStatusForm extends ManagementForm<PlayerStatus> {

    @Nonnull private final PlayerStatusService playerStatusService;

    private TextField nameField;
    private IntegerField bookingsPerDayField;
    private IntegerField bookableDaysInAdvanceField;

    @Autowired
    public PlayerStatusForm(@Nonnull final ButtonFactory buttonFactory,
                            @Nonnull final PlayerStatusService playerStatusService,
                            @Nonnull final InputFieldFactory inputFieldFactory) {
        super(buttonFactory, inputFieldFactory);
        this.playerStatusService = playerStatusService;
    }

    @Override
    protected void instantiateFields() {
        nameField = getInputFieldFactory().createTextField("Name");
        bookingsPerDayField = getInputFieldFactory().createBasicIntegerField("Buchungen pro Tag");
        bookableDaysInAdvanceField = getInputFieldFactory().createBasicIntegerField("Buchbare Tage in die Zukunft");
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
        return new VerticalLayout(getNameField(), bookingsPerDayField, bookableDaysInAdvanceField);
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
        return nameField;
    }

    @Override
    protected boolean isDeleteEnabled() {
        return playerStatusService.findAll().size() > 1;
    }

    @Nonnull
    @Override
    protected Dialog getDeleteConfirmationDialog() {
        final CancelableDialog deleteDialog = new CancelableDialog();
        deleteDialog.setHeaderTitle(currentItem.getIdentifier());

        final Html text = new Html("<div>Status wirklich löschen?<br><br>Jedem Benutzer mit diesem Status<br>wird ein neuer Status zugewiesen.</div>");

        final List<PlayerStatus> remainingPlayerStatus = playerStatusService.findAll()
                .stream()
                .filter(playerStatus -> !playerStatus.equals(currentItem))
                .toList();

        final Select<PlayerStatus> replacementPlayerStatusSelect = new Select<>();
        replacementPlayerStatusSelect.setLabel("Neuer Status");
        replacementPlayerStatusSelect.setItemLabelGenerator(PlayerStatus::getIdentifier);
        replacementPlayerStatusSelect.setItems(remainingPlayerStatus);
        remainingPlayerStatus.stream().findFirst().ifPresent(replacementPlayerStatusSelect::setValue);

        final Button deleteButton = new Button("Löschen");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(event -> {
            fireEvent(new PlayerStatusFormDeleteEvent(this, currentItem, replacementPlayerStatusSelect.getValue()));
            deleteDialog.close();
        });

        final VerticalLayout content = new VerticalLayout();
        content.add(text, replacementPlayerStatusSelect);
        deleteDialog.add(content);
        deleteDialog.getFooter().add(deleteButton);

        return deleteDialog;
    }

    public void addDeleteEventListenerCustom(@Nonnull final ComponentEventListener<PlayerStatusFormDeleteEvent> listener) {
        addListener(PlayerStatusFormDeleteEvent.class, listener);
    }
}