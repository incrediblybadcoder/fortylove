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
import com.vaadin.flow.data.validator.EmailValidator;
import jakarta.annotation.Nonnull;

import java.util.List;

public class UserForm extends FormLayout {
    @Nonnull private final TextField firstName = new TextField("Vorname");
    @Nonnull private final TextField lastName = new TextField("Nachname");
    @Nonnull private final TextField email = new TextField("Email");
    @Nonnull private final ComboBox<PlayerStatus> status = new ComboBox<>("Status");
    @Nonnull private final List<PlayerStatus> availableStatus;

    private Button save;
    private Button update;
    private Button delete;
    private Button close;
    Binder<UserFormBackingBean> binder = new BeanValidationBinder<>(UserFormBackingBean.class); //BeanValidationBider oder nur Binder?

    public UserForm(final List<PlayerStatus> availableStatus) {
        this.availableStatus = availableStatus;
        addClassName("user-form");

        binder.forField(email)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige Email-Adresse ein"))
                .bind(UserFormBackingBean::getEmail, UserFormBackingBean::setEmail);

        binder.bindInstanceFields(this);
        constructUI();
    }

    private void constructUI() {
        add(createInputFieldsLayout());
        initializeButtons();
        createButtonsLayout();
        setStatusComboBoxItems();
    }

    private void setStatusComboBoxItems() {
        status.setItems(availableStatus);
        status.setItemLabelGenerator(PlayerStatus::getName);
    }

    private void initializeButtons() {
        save = new Button("Speichern");
        update = new Button("Aktualisieren");
        delete = new Button("Löschen");
        close = new Button("Abbrechen");
    }

    private VerticalLayout createInputFieldsLayout() {
        VerticalLayout inputFieldsLayout = new VerticalLayout();
        inputFieldsLayout.add(firstName, lastName, email, status);
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
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean().toUserFormInformations())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));
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
        if (user != null) {
            UserFormBackingBean backingBean = new UserFormBackingBean(status);
            backingBean.setFirstName(user.getFirstName());
            backingBean.setLastName(user.getLastName());
            backingBean.setEmail(user.getEmail());
            backingBean.setId(user.getId());
            backingBean.setStatus(user.getPlayerStatus());
            binder.setBean(backingBean);
        }
    }

    private void validateAndUpdate() {
        if(binder.isValid()){
            fireEvent(new UpdateEvent(this, binder.getBean().toUserFormInformations()));
        }
    }

    private void validateAndSave() {
        if(binder.isValid()){
            fireEvent(new SaveEvent(this, binder.getBean().toUserFormInformations()));
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

