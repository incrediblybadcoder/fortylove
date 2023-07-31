package ch.fortylove.presentation.views.management.usermanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.presentation.components.managementform.ManagementForm;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@SpringComponent
@UIScope
public class UserForm extends ManagementForm<User> {

    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final UserFactory userFactory;

    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private Select<PlayerStatus> playerStatus;
    private MultiSelectComboBox<Role> roleCheckboxGroup;

    @Autowired
    public UserForm(@Nonnull final PlayerStatusService playerStatusService,
                    @Nonnull final RoleService roleService,
                    @Nonnull final UserFactory userFactory) {
        this.playerStatusService = playerStatusService;
        this.roleService = roleService;
        this.userFactory = userFactory;
    }

    @Override
    protected void instantiateFields() {
        firstName = new TextField();
        lastName = new TextField();
        email = new TextField();
        playerStatus = new Select<>();
        roleCheckboxGroup = new MultiSelectComboBox<>();
    }

    @Override
    protected Binder<User> getBinder() {
        final Binder<User> binder = new Binder<>(User.class);

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

        binder.forField(playerStatus).bind(User::getPlayerStatus, User::setPlayerStatus);

        binder.forField(roleCheckboxGroup)
                .withValidator((Validator<Set<Role>>) (value, context) -> {
                    if (value == null || value.isEmpty()) {
                        return ValidationResult.error("Mindestens eine Rolle muss ausgewählt sein");
                    }
                    return ValidationResult.ok();
                })
                .bind(User::getRoles, User::setRoles);

        return binder;
    }

    @Override
    protected VerticalLayout getContent() {
        return new VerticalLayout(getFirstNameField(), getLastNameField(), getEmailField(), getPlayerStatusSelection(), getRoleSelection());
    }

    @Nonnull
    private TextField getFirstNameField() {
        firstName.setWidthFull();
        firstName.setLabel("Vorname");
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        firstName.setRequiredIndicatorVisible(true);
        firstName.setRequired(true);
        return firstName;
    }

    @Nonnull
    private TextField getLastNameField() {
        lastName.setWidthFull();
        lastName.setLabel("Nachname");
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        lastName.setRequired(true);
        lastName.setRequiredIndicatorVisible(true);
        return lastName;
    }

    @Nonnull
    private TextField getEmailField() {
        email.setWidthFull();
        email.setLabel("Email");
        email.setValueChangeMode(ValueChangeMode.EAGER);
        email.setRequired(true);
        email.setRequiredIndicatorVisible(true);
        return email;
    }

    @Nonnull
    private Select<PlayerStatus> getPlayerStatusSelection() {
        playerStatus.setWidthFull();
        playerStatus.setLabel("Status");
        playerStatus.setItemLabelGenerator(PlayerStatus::getName);
        return playerStatus;
    }

    @Nonnull
    private MultiSelectComboBox<Role> getRoleSelection() {
        roleCheckboxGroup.setWidthFull();
        roleCheckboxGroup.setLabel("Rollen");
        roleCheckboxGroup.setItemLabelGenerator(Role::getName);
        roleCheckboxGroup.setRequired(true);
        roleCheckboxGroup.setRequiredIndicatorVisible(true);
        return roleCheckboxGroup;
    }

    @Override
    protected User getNewItem() {
        return userFactory.newEmptyDefaultUser();
    }

    @Override
    protected String getItemIdentifier(@Nonnull final User user) {
        return user.getIdentifier();
    }

    @Override
    protected String getItemName() {
        return "Benutzer";
    }

    @Override
    protected void beforeOpen() {
        super.beforeOpen();
        playerStatus.setItems(playerStatusService.findAll());
        roleCheckboxGroup.setItems(roleService.findAll());
    }
}

