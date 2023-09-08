package ch.fortylove.presentation.views.management.usermanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.presentation.components.InputFieldFactory;
import ch.fortylove.presentation.components.managementform.ManagementForm;
import ch.fortylove.presentation.fieldvalidators.FirstNameValidator;
import ch.fortylove.presentation.fieldvalidators.LastNameValidator;
import ch.fortylove.presentation.fieldvalidators.SetNotEmptyValidator;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

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
        firstName = InputFieldFactory.createTextField("Vorname");
        lastName = InputFieldFactory.createTextField("Nachname");
        email = InputFieldFactory.createTextField("Email");
        playerStatus = new Select<>();
        roleCheckboxGroup = new MultiSelectComboBox<>();
    }

    @Nonnull
    @Override
    protected Binder<User> getBinder() {
        final Binder<User> binder = new Binder<>(User.class);

        binder.forField(email)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige Email-Adresse ein"))
                .bind(User::getEmail, User::setEmail);

        binder.forField(firstName)
                .withValidator(FirstNameValidator::validateFirstName)
                .bind(User::getFirstName, User::setFirstName);

        binder.forField(lastName)
                .withValidator(LastNameValidator::validateLastName)
                .bind(User::getLastName, User::setLastName);

        binder.forField(playerStatus).bind(User::getPlayerStatus, User::setPlayerStatus);

        binder.forField(roleCheckboxGroup)
                .withValidator(new SetNotEmptyValidator<>("Bitte wählen Sie mindestens eine Rolle aus"))
                .bind(User::getRoles, User::setRoles);

        return binder;
    }

    @Nonnull
    @Override
    protected VerticalLayout getContent() {
        return new VerticalLayout(getFirstNameField(), getLastNameField(), getEmailField(), getPlayerStatusSelection(), getRoleSelection());
    }

    @Nonnull
    private TextField getFirstNameField() {
        firstName.setWidthFull();
        return firstName;
    }

    @Nonnull
    private TextField getLastNameField() {
        lastName.setWidthFull();
        return lastName;
    }

    @Nonnull
    private TextField getEmailField() {
        email.setWidthFull();
        return email;
    }

    @Nonnull
    private Select<PlayerStatus> getPlayerStatusSelection() {
        playerStatus.setWidthFull();
        playerStatus.setLabel("Spielerstatus");
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

    @Nonnull
    @Override
    protected User getNewItem() {
        return userFactory.newGuestUser();
    }

    @Nonnull
    @Override
    protected String getItemIdentifier(@Nonnull final User user) {
        return user.getIdentifier();
    }

    @Nonnull
    @Override
    protected String getItemName() {
        return "Benutzer";
    }

    @Nonnull
    @Override
    protected Focusable<TextField> getFocusOnOpen() {
        return firstName;
    }

    @Override
    protected void beforeOpen() {
        super.beforeOpen();
        playerStatus.setItems(playerStatusService.findAll());
        roleCheckboxGroup.setItems(roleService.findAll());
    }
}