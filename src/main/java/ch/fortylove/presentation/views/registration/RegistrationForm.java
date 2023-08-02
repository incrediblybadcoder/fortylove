package ch.fortylove.presentation.views.registration;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
@AnonymousAllowed
public class RegistrationForm extends FormLayout {

    @Nonnull private final UserService userService;
    @Nonnull private final PasswordEncoder passwordEncoder;
    @Nonnull private final UserFactory userFactory;

    @Nonnull final private Binder<User> binder;

    private H2 title;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private PasswordField plainPassword;
    private PasswordField confirmPlainPassword;
    private Button register;
    private Button cancel;

    private User user;

    private String plainPasswordInput;
    private String confirmPlainPasswordInput;

    public RegistrationForm(@Nonnull final UserService userService,
                            @Nonnull final PasswordEncoder passwordEncoder,
                            @Nonnull final UserFactory userFactory) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userFactory = userFactory;
        this.binder = new Binder<>(User.class);

        binder.bindInstanceFields(this);

        constructUI();
        defineValidators();
        defineListeners();
    }

    @Override
    protected void onAttach(@Nonnull final AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        user = userFactory.newEmptyDefaultUser();
        binder.setBean(user);
    }

    private void defineListeners() {
        binder.addValueChangeListener(valueChangeEvent -> updateButtonState());

        register.addClickListener(buttonClickEvent -> {
            if (binder.writeBeanIfValid(user)) {
                if (userService.findByEmail(user.getEmail()).isPresent()) {
                    NotificationUtil.errorNotification("Es gibt bereits einen Benutzer mit dieser Email-Adresse.");
                } else {
                    user.getAuthenticationDetails().setEncryptedPassword(passwordEncoder.encode(plainPasswordInput));
                    userService.create(user);
                    buttonClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate("login"));
                    NotificationUtil.infoNotification("Registration erfolgreich");
                }
            }
        });

        cancel.addClickListener(buttonClickEvent -> buttonClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate("login")));
    }

    private void defineValidators() {
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

        binder.forField(email)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige Email-Adresse ein"))
                .bind(User::getEmail, User::setEmail);

        binder.forField(plainPassword)
                .withValidator((Validator<String>) (value, context) -> {
                    if (value.isEmpty()) {
                        return ValidationResult.error("Passwort darf nicht leer sein");
                    } else if (value.length() < 8) {
                        return ValidationResult.error("Das Passwort muss mindestens 8 Zeichen lang sein");
                    }
                    return ValidationResult.ok();
                })
                .bind(user -> plainPasswordInput, (user, value) -> plainPasswordInput = value);

        binder.forField(confirmPlainPassword)
                .withValidator((Validator<String>) (value, context) -> {
                    if (!value.equals(plainPassword.getValue())) {
                        return ValidationResult.error("Passwörter stimmen nicht überein");
                    }
                    return ValidationResult.ok();
                })
                .bind(user -> confirmPlainPasswordInput, (user, value) -> confirmPlainPasswordInput = value);
    }

    private void updateButtonState() {
        final boolean valid = binder.isValid();
        register.setEnabled(valid);
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
        firstName = new TextField("Vorname");
        lastName = new TextField("Nachname");
        email = new TextField("Email");
        plainPassword = new PasswordField("Passwort");
        confirmPlainPassword = new PasswordField("Passwort bestätigen");
        register = new Button("Registrieren");
        cancel = new Button("Abbrechen");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        email.setValueChangeMode(ValueChangeMode.EAGER);
        plainPassword.setValueChangeMode(ValueChangeMode.EAGER);
        confirmPlainPassword.setValueChangeMode(ValueChangeMode.EAGER);
        firstName.setRequired(true);
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequired(true);
        lastName.setRequiredIndicatorVisible(true);
        email.setRequired(true);
        email.setRequiredIndicatorVisible(true);
        plainPassword.setRequired(true);
        plainPassword.setRequiredIndicatorVisible(true);
        confirmPlainPassword.setRequired(true);
        confirmPlainPassword.setRequiredIndicatorVisible(true);
    }
}
