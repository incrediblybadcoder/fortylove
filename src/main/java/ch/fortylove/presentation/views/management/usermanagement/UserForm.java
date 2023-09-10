package ch.fortylove.presentation.views.management.usermanagement;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.Role;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.UserStatus;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.presentation.components.InputFieldFactory;
import ch.fortylove.presentation.components.managementform.ManagementForm;
import ch.fortylove.presentation.components.managementform.OpenMode;
import ch.fortylove.presentation.fieldvalidators.NameValidator;
import ch.fortylove.presentation.fieldvalidators.SetNotEmptyValidator;
import ch.fortylove.service.PlayerStatusService;
import ch.fortylove.service.RoleService;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserForm extends ManagementForm<User> {

    @Nonnull private final PlayerStatusService playerStatusService;
    @Nonnull private final RoleService roleService;
    @Nonnull private final UserFactory userFactory;
    @Nonnull private final PasswordEncoder passwordEncoder;

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private PasswordField passwordField;
    private Select<UserStatus> userStatusSelection;
    private Select<PlayerStatus> playerStatusSelection;
    private MultiSelectComboBox<Role> roleMultiSelectComboBox;

    @Autowired
    public UserForm(@Nonnull final PlayerStatusService playerStatusService,
                    @Nonnull final RoleService roleService,
                    @Nonnull final UserFactory userFactory,
                    @Nonnull final PasswordEncoder passwordEncoder) {
        this.playerStatusService = playerStatusService;
        this.roleService = roleService;
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void instantiateFields() {
        firstNameField = InputFieldFactory.createTextField("Vorname");
        lastNameField = InputFieldFactory.createTextField("Nachname");
        emailField = InputFieldFactory.createTextField("Email");
        passwordField = InputFieldFactory.createPasswordField("Password");
        userStatusSelection = new Select<>();
        playerStatusSelection = new Select<>();
        roleMultiSelectComboBox = new MultiSelectComboBox<>();
    }

    @Nonnull
    @Override
    protected Binder<User> getBinder() {
        final Binder<User> binder = new Binder<>(User.class);

        binder.forField(firstNameField)
                .withValidator(new NameValidator("Vorname"))
                .bind(User::getFirstName, User::setFirstName);

        binder.forField(lastNameField)
                .withValidator(new NameValidator("Nachname"))
                .bind(User::getLastName, User::setLastName);

        binder.forField(emailField)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige Email-Adresse ein"))
                .bind(User::getEmail, User::setEmail);

        binder.forField(passwordField)
                .bind(user -> user.getAuthenticationDetails().getEncryptedPassword(), (user, value) -> user.getAuthenticationDetails().setEncryptedPassword(value));

        binder.forField(userStatusSelection)
                .bind(User::getUserStatus, User::setUserStatus);

        binder.forField(playerStatusSelection)
                .bind(User::getPlayerStatus, User::setPlayerStatus);

        binder.forField(roleMultiSelectComboBox)
                .withValidator(new SetNotEmptyValidator<>("Bitte wählen Sie mindestens eine Rolle aus"))
                .bind(User::getRoles, User::setRoles);

        return binder;
    }

    @Nonnull
    @Override
    protected VerticalLayout getContent() {
        return new VerticalLayout(getFirstNameField(), getLastNameField(), getEmailField(), getPasswordField(), getUserStatusSelection(), getPlayerStatusSelection(), getRoleSelection());
    }

    @Nonnull
    private TextField getFirstNameField() {
        firstNameField.setWidthFull();
        return firstNameField;
    }

    @Nonnull
    private TextField getLastNameField() {
        lastNameField.setWidthFull();
        return lastNameField;
    }

    @Nonnull
    private TextField getEmailField() {
        emailField.setWidthFull();
        // Autocomplete.OFF is ignored by most browsers, use Autocomplete.NEW_PASSWORD
        emailField.setAutocomplete(Autocomplete.NEW_PASSWORD);
        return emailField;
    }

    @Nonnull
    private PasswordField getPasswordField() {
        passwordField.setWidthFull();
        // Autocomplete.OFF is ignored by most browsers, use Autocomplete.NEW_PASSWORD
        passwordField.setAutocomplete(Autocomplete.NEW_PASSWORD);
        return passwordField;
    }

    @Nonnull
    private Select<UserStatus> getUserStatusSelection() {
        userStatusSelection.setWidthFull();
        userStatusSelection.setLabel("Benutzerstatus");
        userStatusSelection.setItemLabelGenerator(UserStatus::getName);
        return userStatusSelection;
    }

    @Nonnull
    private Select<PlayerStatus> getPlayerStatusSelection() {
        playerStatusSelection.setWidthFull();
        playerStatusSelection.setLabel("Spielerstatus");
        playerStatusSelection.setItemLabelGenerator(PlayerStatus::getName);
        return playerStatusSelection;
    }

    @Nonnull
    private MultiSelectComboBox<Role> getRoleSelection() {
        roleMultiSelectComboBox.setWidthFull();
        roleMultiSelectComboBox.setLabel("Rollen");
        roleMultiSelectComboBox.setItemLabelGenerator(Role::getName);
        roleMultiSelectComboBox.setRequired(true);
        roleMultiSelectComboBox.setRequiredIndicatorVisible(true);
        return roleMultiSelectComboBox;
    }

    @Nonnull
    @Override
    protected User getNewItem() {
        return userFactory.newEmptyGuestUser(true);
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
        return firstNameField;
    }

    @Override
    protected void beforeOpen(@Nonnull final OpenMode openMode,
                              @Nonnull final User currentUser) {
        super.beforeOpen(openMode, currentItem);

        final List<UserStatus> manuallyManageableUserStatus = UserStatus.getManuallyManageableUserStatus();
        final List<UserStatus> selectableUserStatus = manuallyManageableUserStatus.contains(currentUser.getUserStatus()) ?
                manuallyManageableUserStatus :
                List.of(UserStatus.values());
        userStatusSelection.setItems(selectableUserStatus);

        playerStatusSelection.setItems(playerStatusService.findAll());
        roleMultiSelectComboBox.setItems(roleService.findAll());
    }

    @Override
    protected void afterOpen(@Nonnull final OpenMode openMode,
                             @Nonnull final User currentUser) {
        super.afterOpen(openMode, currentUser);

        // on createMode show password field and clear it
        // on update/delete mode don't show password field to prevent changing of password
        // on update/delete mode binder writes password back as it was when loading it
        final boolean isCreateMode = openMode.equals(OpenMode.NEW);
        passwordField.setVisible(isCreateMode);
        if (isCreateMode) {
            passwordField.clear();
        }
    }

    @Override
    protected void saveClick() {
        // password is entered in plain form and needs to be encrypted before saving
        // binder sets password from field directly into authenticationDetails, which expects an ecnrypted password
        encryptPasswordOnCreateMode();
        super.saveClick();
    }

    private void encryptPasswordOnCreateMode() {
        if (openMode.equals(OpenMode.NEW)) {
            final String plainPassword = passwordField.getValue();
            final String encryptedPassword = passwordEncoder.encode(plainPassword);
            passwordField.setValue(encryptedPassword);
        }
    }
}