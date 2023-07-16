package ch.fortylove.view.membermanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.view.membermanagement.events.CloseEvent;
import ch.fortylove.view.membermanagement.events.DeleteEvent;
import ch.fortylove.view.membermanagement.events.SaveEvent;
import ch.fortylove.view.membermanagement.events.UpdateEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
import jakarta.annotation.Nonnull;

import java.util.List;

public class UserForm extends FormLayout {
    @Nonnull private final TextField firstName ;
    @Nonnull private final TextField lastName;
    @Nonnull private final TextField email;
    @Nonnull private final ComboBox<PlayerStatus> playerStatus;
    @Nonnull private final List<PlayerStatus> availableStatus;

    private Button save;
    private Button update;
    private Button delete;
    private Button close;
    @Nonnull final private Binder<User> binder;

    public UserForm(final List<PlayerStatus> availableStatus) {
        addClassName("user-form");

        this.firstName = new TextField("Vorname");
        this.lastName = new TextField("Nachname");
        this.email = new TextField("Email");
        this.playerStatus = new ComboBox<>("Status");
        this.availableStatus = availableStatus;
        constructUI();

        this.binder = new BeanValidationBinder<>(User.class); //BeanValidationBider oder nur Binder?;
//        defineInputFieldInputClickListener();
        defineValidators();
        binder.addValueChangeListener(inputEvent -> {
            updateButtonState();
        });

        binder.bindInstanceFields(this);


    }

    private void defineInputFieldInputClickListener() {
        firstName.addInputListener(inputEvent -> {
            updateButtonState();
        });

        lastName.addInputListener(inputEvent -> {
            updateButtonState();
        });

        email.addInputListener(inputEvent -> {
            updateButtonState();
        });

        playerStatus.addValueChangeListener(inputEvent -> {
            updateButtonState();
        });
    }

    private void defineValidators() {
        binder.forField(email)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige Email-Adresse ein"))
                .bind(User::getEmail, User::setEmail);


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
    }

    private void constructUI() {
        add(createInputFieldsLayout());
        initializeButtons();
        createButtonsLayout();
        setStatusComboBoxItems();
    }

    private void setStatusComboBoxItems() {
        playerStatus.setItems(availableStatus);
        playerStatus.setItemLabelGenerator(PlayerStatus::getName);
    }

    private void initializeButtons() {
        save = new Button("Speichern");
        update = new Button("Aktualisieren");
        delete = new Button("Löschen");
        close = new Button("Abbrechen");
    }

    private VerticalLayout createInputFieldsLayout() {
        VerticalLayout inputFieldsLayout = new VerticalLayout();
        inputFieldsLayout.add(firstName, lastName, email, playerStatus);
        return inputFieldsLayout;
    }

    private void createButtonsLayout() {
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

    private void updateButtonState() {
        final boolean ok = binder.isValid();
        save.setEnabled(ok);
        update.setEnabled(ok);
        delete.setEnabled(true);
        close.setEnabled(true);
    }

    public void createUserForm(){
        removeAll();
        add(createInputFieldsLayout(), save, close);
    }

    public void updateUserForm(){
        removeAll();
        add(createInputFieldsLayout(), update, delete, close);
    }

    public void setUser(User user) {
        resetForm();
        binder.removeBean();
        if (user != null) {
            binder.setBean(user);
            binder.readBean(user);
            binder.validate();
        }
    }

    public void resetForm() {
        binder.setBean(null);

        firstName.clear();
        lastName.clear();
        email.clear();
        playerStatus.clear();
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

