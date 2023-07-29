package ch.fortylove.presentation.views.registration;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.persistence.entity.factory.UserFactory;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Nonnull;

@Route("registration")
@PageTitle("Registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    @Nonnull private final UserService userService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    @Nonnull final private Binder<User> binder;
    @Nonnull private final Button register = new Button("Registrieren");
    @Nonnull private final Button abbrechen = new Button("Abbrechen");

    @Nonnull private final TextField email = new TextField("Email");
    @Nonnull private final TextField firstName = new TextField("Vorname");
    @Nonnull private final TextField lastName = new TextField("Nachname");
    @Nonnull private final PasswordField plainPassword = new PasswordField("Passwort");
    @Nonnull private final PasswordField confirmPlainPassword = new PasswordField("Passwort bestätigen");

    @Nonnull private final User user;

    private String plainPasswordInput;
    private String confirmPlainPasswordInput;

    public RegistrationView(@Nonnull final UserService userService,
                            @Nonnull final PasswordEncoder passwordEncoder,
                            @Nonnull final UserFactory userFactory) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.binder = new Binder<>(User.class);

        defineListeners();
        defineValidators();
        binder.bindInstanceFields(this);

        user = userFactory.newEmptyDefaultUser();
        binder.setBean(user);

        constructUI();
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

        abbrechen.addClickListener(buttonClickEvent -> buttonClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate("login")));
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
        add(new H2("Registration"), createInputFieldsLayout(), buttonsLayout());
        setSizeFull();
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    @Nonnull
    private Component createInputFieldsLayout() {
        final VerticalLayout inputFieldsLayout = new VerticalLayout();
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
        inputFieldsLayout.add(firstName, lastName, email, plainPassword, confirmPlainPassword);
        inputFieldsLayout.setAlignItems(Alignment.CENTER);
        return inputFieldsLayout;
    }

    @Nonnull
    private VerticalLayout buttonsLayout() {
        final VerticalLayout buttonsLayout = new VerticalLayout();
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        abbrechen.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonsLayout.add(register, abbrechen);
        buttonsLayout.setAlignItems(Alignment.CENTER);
        return buttonsLayout;
    }
}