package ch.fortylove.presentation.views.registration;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import ch.fortylove.util.fieldvalidators.FirstNameValidator;
import ch.fortylove.util.fieldvalidators.LastNameValidator;
import ch.fortylove.util.uielements.ButtonsUtil;
import ch.fortylove.util.uielements.InputFieldsUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AnonymousAllowed
public class RegistrationForm extends FormLayout {


    @Nonnull private final UserService userService;
    @Nonnull private final PasswordEncoder passwordEncoder;
    @Nonnull final private Binder<User> binder;
    private H2 title;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private PasswordField plainPassword;
    private PasswordField confirmPlainPassword;
    private Button register;
    private Button cancel;
    private final User user;
    private String confirmPlainPasswordInput;

    public RegistrationForm(@Nonnull final UserService userService,
                            @Nonnull final PasswordEncoder passwordEncoder,
                            @Nonnull final UserFactory userFactory) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

        this.binder = new Binder<>(User.class);
        binder.bindInstanceFields(this);

        user = userFactory.newEmptyDefaultUser();
        binder.setBean(user);
        binder.addValueChangeListener(valueChangeEvent -> updateButtonState());

        constructUI();
        defineValidators();
    }

    private void constructUI() {
        initUIElements();
        add(title, firstName, lastName, email, plainPassword, confirmPlainPassword, register, cancel);

        setMaxWidth("1000px");
        setResponsiveSteps(new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490", 2, ResponsiveStep.LabelsPosition.TOP));
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(register, 2);
        setColspan(cancel, 2);
    }

    private void initUIElements() {
        title = new H2("Registrierung");
        firstName = InputFieldsUtil.createTextField("Vorname");
        lastName = InputFieldsUtil.createTextField("Nachname");
        email = InputFieldsUtil.createTextField("Email");
        plainPassword = InputFieldsUtil.createPasswordField("Passwort");
        confirmPlainPassword = InputFieldsUtil.createConfirmationPasswordField("Passwort bestätigen");
        register = ButtonsUtil.createPrimaryButton("Registrieren", this::registerClick);
        cancel = ButtonsUtil.createNeutralButton("Abbrechen", this::gotToLoginPage);
    }

    private void gotToLoginPage(final ClickEvent<Button> buttonClickEvent) {
        buttonClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate("login"));
    }

    private void registerClick(final ClickEvent<Button> buttonClickEvent) {
        if (binder.writeBeanIfValid(user)) {
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                NotificationUtil.errorNotification("Es gibt bereits einen Benutzer mit dieser Email-Adresse.");
            } else {
                user.getAuthenticationDetails().setEncryptedPassword(passwordEncoder.encode(plainPassword.getValue()));
                userService.create(user);
                gotToLoginPage(buttonClickEvent);
                NotificationUtil.infoNotification("Registration erfolgreich");
            }
        }
    }

    private void defineValidators() {
        binder.forField(firstName)
                .withValidator(FirstNameValidator::validateFirstName)
                .bind(User::getFirstName, User::setFirstName);

        binder.forField(lastName)
                .withValidator(LastNameValidator::validateLastName)
                .bind(User::getLastName, User::setLastName);

        binder.forField(email)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige Email-Adresse ein"))
                .bind(User::getEmail, User::setEmail);

        binder.forField(confirmPlainPassword)
                .withValidator(this::validateConfirmationPassword)
                .bind(user -> confirmPlainPasswordInput, (user, value) -> confirmPlainPasswordInput = value);
    }

    @Nonnull
    private ValidationResult validateConfirmationPassword(String confirmPlainPasswordValue, ValueContext context) {
        if (confirmPlainPasswordValue != null && !confirmPlainPasswordValue.equals(plainPassword.getValue())) {
            return ValidationResult.error("Passwörter stimmen nicht überein");
        }
        return ValidationResult.ok();
    }

    private void updateButtonState() {
        final boolean valid = binder.isValid();
        register.setEnabled(valid);
    }
}
