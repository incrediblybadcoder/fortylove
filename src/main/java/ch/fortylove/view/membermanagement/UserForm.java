package ch.fortylove.view.membermanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.view.membermanagement.events.CloseEvent;
import ch.fortylove.view.membermanagement.events.DeleteEvent;
import ch.fortylove.view.membermanagement.events.SaveEvent;
import ch.fortylove.view.membermanagement.events.UpdateEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserForm extends FormLayout {
    @Nonnull private final TextField firstName ;
    @Nonnull private final TextField lastName;
    @Nonnull private final TextField email;
    @Nonnull private final ComboBox<PlayerStatus> playerStatus;
    @Nonnull private final List<PlayerStatus> availableStatus;

    @Nonnull private final CheckboxGroup<Role> roleCheckboxGroup;

    @Nonnull private final Optional<List<Role>> availableRoles;

    private Button save;
    private Button update;
    private Button delete;
    private Button close;
    private VerticalLayout buttonContainer;
    @Nonnull final private Binder<User> binder;

    public UserForm(final List<PlayerStatus> availableStatus, Optional<List<Role>> availableRoles) {
        addClassName("user-form");

        this.firstName = new TextField("Vorname");
        this.lastName = new TextField("Nachname");
        this.email = new TextField("Email");
        this.playerStatus = new ComboBox<>("Status");
        this.availableStatus = availableStatus;
        this.roleCheckboxGroup = new CheckboxGroup<>();
        this.availableRoles = availableRoles;

        constructUI();

        this.binder = new Binder<>(User.class);
        defineValidators();
        binder.addValueChangeListener(inputEvent -> updateButtonState());

        binder.bindInstanceFields(this);


    }
    private void defineValidators() {
        binder.forField(email)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige Email-Adresse ein"))
                .bind(User::getEmail, User::setEmail);

        binder.forField(firstName)
                .withValidator((Validator<String>) (value, context) -> {
                    if (value.isEmpty()) {
                        return ValidationResult.error("Der Vorname darf nicht leer sein");
                    } else if (value.length() > 50) {
                        return ValidationResult.error("Der Vorname darf maximal 50 Zeichen haben");
                    }
                    return ValidationResult.ok();
                })
                .bind(User::getFirstName, User::setFirstName);


        binder.forField(lastName)
                .withValidator((Validator<String>) (value, context) -> {
                    if (value.isEmpty()) {
                        return ValidationResult.error("Der Nachname darf nicht leer sein");
                    } else if (value.length() > 50) {
                        return ValidationResult.error("Der Nachname darf maximal 50 Zeichen haben");
                    }
                    return ValidationResult.ok();
                })
                .bind(User::getLastName, User::setLastName);


        binder.forField(playerStatus)
                .withValidator((Validator<PlayerStatus>) (value, context) -> {
                    if (value == null) {
                        return ValidationResult.error("Bitte wählen Sie einen gültigen Status aus");
                    }
                    return ValidationResult.ok();
                })
                .bind(User::getPlayerStatus, User::setPlayerStatus);

        binder.forField(roleCheckboxGroup)
                .withValidator((Validator<Set<Role>>) (value, context) -> {
                    if (value == null || value.isEmpty()) {
                        return ValidationResult.error("Mindestens eine Rolle muss ausgewählt sein");
                    }
                    return ValidationResult.ok();
                })
                .bind(User::getRoles, User::setRoles);


    }

    private void constructUI() {
        initializeButtons();
        initializeButtonsContainer();
        setStatusComboBoxItems();
        initializeCheckboxGroup();
        add(createInputFieldsLayout(), roleCheckboxGroup, buttonContainer);
    }

    private void initializeCheckboxGroup() {
        roleCheckboxGroup.setLabel("Rollen");
        roleCheckboxGroup.setItemLabelGenerator(Role::getName);
        availableRoles.ifPresent(roleCheckboxGroup::setItems);
        roleCheckboxGroup.setRequired(true);
        roleCheckboxGroup.setRequiredIndicatorVisible(true);
        roleCheckboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    }

    private void setStatusComboBoxItems() {
        playerStatus.setItems(availableStatus);
        playerStatus.setItemLabelGenerator(PlayerStatus::getName);
        playerStatus.setAllowCustomValue(false);
    }

    private void initializeButtonsContainer() {
        buttonContainer = new VerticalLayout();
        buttonContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }

    private void initializeButtons() {
        save = new Button("Speichern");
        update = new Button("Aktualisieren");
        delete = new Button("Löschen");
        close = new Button("Abbrechen");

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        update.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        update.addClickListener(click -> validateAndUpdate());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));
    }

    private VerticalLayout createInputFieldsLayout() {
        VerticalLayout inputFieldsLayout = new VerticalLayout();
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        email.setValueChangeMode(ValueChangeMode.EAGER);
        firstName.setRequired(true);
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequired(true);
        lastName.setRequiredIndicatorVisible(true);
        email.setRequired(true);
        email.setRequiredIndicatorVisible(true);
        playerStatus.setRequired(true);
        playerStatus.setRequiredIndicatorVisible(true);
        inputFieldsLayout.add(firstName, lastName, email, playerStatus);
        return inputFieldsLayout;
    }

    private void updateButtonState() {
        final boolean ok = binder.isValid();
        save.setEnabled(ok);
        update.setEnabled(ok);
        delete.setEnabled(true);
        close.setEnabled(true);
    }

    public void createUserForm(){
        addButtons(save, close);
    }

    public void updateUserForm(){
        addButtons(update, delete, close);
    }

    private void addButtons(@Nonnull final Button... buttons) {
        buttonContainer.removeAll();
        buttonContainer.add(buttons);
    }

    public void setUser(User user) {
            binder.setBean(user);
    }


    private void validateAndUpdate() {
        if(binder.isValid()){
            fireEvent(new UpdateEvent(this, binder.getBean()));
        }
    }

    private void validateAndSave() {
        if(binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }
    public void addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        addListener(DeleteEvent.class, listener);
    }

    public void addSaveListener(ComponentEventListener<SaveEvent> listener) {
        addListener(SaveEvent.class, listener);
    }

    public void addUpdateListener(ComponentEventListener<UpdateEvent> listener) {
        addListener(UpdateEvent.class, listener);
    }
    public void addCloseListener(ComponentEventListener<CloseEvent> listener) {
        addListener(CloseEvent.class, listener);
    }
}

