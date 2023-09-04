package ch.fortylove.presentation.views.registration;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.presentation.components.ButtonFactory;
import ch.fortylove.presentation.components.InputFieldFactory;
import ch.fortylove.presentation.fieldvalidators.FirstNameValidator;
import ch.fortylove.presentation.fieldvalidators.LastNameValidator;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
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
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AnonymousAllowed
public class RegistrationForm extends FormLayout {

    @Nonnull private final UserService userService;
    @Nonnull private final NotificationUtil notificationUtil;
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
    private String plainPasswordInput;

    public RegistrationForm(@Nonnull final UserService userService,
                            @Nonnull final NotificationUtil notificationUtil,
                            @Nonnull final PasswordEncoder passwordEncoder,
                            @Nonnull final UserFactory userFactory) {
        this.userService = userService;
        this.notificationUtil = notificationUtil;
        this.passwordEncoder = passwordEncoder;

        binder = new Binder<>(User.class);
        binder.bindInstanceFields(this);

        user = userFactory.newEmptyDefaultUser();
        binder.setBean(user);
        binder.addValueChangeListener(valueChangeEvent -> updateButtonState());

        constructUI();
        defineValidators();

        updateButtonState();
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
        firstName = InputFieldFactory.createTextField("Vorname");
        lastName = InputFieldFactory.createTextField("Nachname");
        email = InputFieldFactory.createTextField("E-Mail");
        plainPassword = InputFieldFactory.createPasswordField("Passwort");
        plainPassword.addValueChangeListener(event -> binder.validate());
        confirmPlainPassword = InputFieldFactory.createConfirmationPasswordField("Passwort bestätigen");
        register = ButtonFactory.createPrimaryButton("Registrieren", this::registerClick);
        cancel = ButtonFactory.createNeutralButton("Abbrechen", this::gotToLoginPage);
    }

    private void gotToLoginPage(@Nonnull final ClickEvent<Button> buttonClickEvent) {
        buttonClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate("login"));
    }

    private void registerClick(@Nonnull final ClickEvent<Button> buttonClickEvent) {
        if (binder.writeBeanIfValid(user)) {
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                notificationUtil.errorNotification("Es gibt bereits einen Benutzer mit dieser E-Mail-Adresse.");
            } else {
                user.getAuthenticationDetails().setEncryptedPassword(passwordEncoder.encode(plainPassword.getValue()));
                userService.create(user, true);
                gotToLoginPage(buttonClickEvent);
                notificationUtil.informationNotification("Überprüfe deine E-Mails um deine Registrierung abzuschliessen.");
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
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige E-Mail-Adresse ein"))
                .bind(User::getEmail, User::setEmail);


        binder.forField(plainPassword)
                .withValidator(password -> password != null && !password.trim().isEmpty(), "Passwort darf nicht leer sein.")
                .bind(instance -> plainPasswordInput, (instance, value) -> plainPasswordInput = value);


        binder.forField(confirmPlainPassword)
                .withValidator(this::validateConfirmationPassword)
                .bind(user -> confirmPlainPasswordInput, (user, value) -> confirmPlainPasswordInput = value);
    }

    @Nonnull
    private ValidationResult validateConfirmationPassword(@Nullable final String confirmPlainPasswordValue,
                                                          @Nonnull final ValueContext context) {
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
