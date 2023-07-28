package ch.fortylove.presentation.views;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.security.AuthenticationService;
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

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

@Route("registration")
@PageTitle("Registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout{

    @Nonnull private final AuthenticationService authenticationService;

    @Nonnull final private Binder<User> binder;
    private final Button register = new Button("Registrieren");
    private final Button abbrechen = new Button("Abbrechen");


    private final TextField email = new TextField("Email");
    private final TextField firstName = new TextField("Vorname");
    private final TextField lastName = new TextField("Nachname");
    private final PasswordField plainPassword = new PasswordField("Passwort");
    private final PasswordField confirmPlainPassword = new PasswordField("Passwort bestätigen");

    public RegistrationView(@Nonnull final AuthenticationService authenticationService){
        addClassName("registration-view");
        this.authenticationService = authenticationService;
        this.binder = new Binder<>(User.class);

        defineListeners();
        defineValidators();
        binder.bindInstanceFields(this);

        constructUI();
    }

    private void defineListeners() {
        binder.addValueChangeListener(valueChangeEvent -> updateButtonState());

        register.addClickListener(buttonClickEvent -> {
            final boolean register = register(email.getValue(), firstName.getValue(), lastName.getValue(), plainPassword.getValue());
            if (register){
                buttonClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });

        abbrechen.addClickListener(buttonClickEvent -> buttonClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate("login")));
    }

    private void defineValidators() {
        final AtomicReference<String> plainPasswordValue = new AtomicReference<>();

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

        binder.forField(plainPassword)
                .withValidator((Validator<String>) (value, context) -> {
                    if (value.isEmpty()) {
                        return ValidationResult.error("Passwort darf nicht leer sein");
                    } else if (value.length() < 8) {
                        return ValidationResult.error("Das Passwort muss mindestens 8 Zeichen lang sein");
                    }
                    plainPasswordValue.set(value);
                    return ValidationResult.ok();
                })
                .bind(User::getEncryptedPassword, (user, value) -> {});

        binder.forField(confirmPlainPassword)
                .withValidator((Validator<String>) (value, context) -> {
                    if (!value.equals(plainPassword.getValue())) {
                        return ValidationResult.error("Passwörter stimmen nicht überein");
                    }
                    return ValidationResult.ok();
                })
                .bind(User::getEncryptedPassword, (user, value) -> {});
    }

    private void updateButtonState() {
        final boolean valid = binder.isValid();
        register.setEnabled(valid);
    }

    private void constructUI() {
        add(new H2("Registration"), createInputFildsLayout(), buttonsLayout());
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private Component createInputFildsLayout() {
        VerticalLayout inputFieldsLayout = new VerticalLayout();
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

    private VerticalLayout buttonsLayout() {
        final VerticalLayout buttonsLayout = new VerticalLayout();
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        abbrechen.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonsLayout.add(register, abbrechen);
        buttonsLayout.setAlignItems(Alignment.CENTER);
        return buttonsLayout;
    }

    private boolean register(@Nonnull String email, @Nonnull String firstName, @Nonnull String lastName, @Nonnull String password) {
        if (binder.isValid()) {
            if (authenticationService.register(firstName, lastName, email, password)) {
                NotificationUtil.infoNotification("Registration erfolgreich");
                return true;
            } else {
                NotificationUtil.errorNotification("Es gibt bereits einen Benutzer mit dieser Email-Adresse.");
                return false;
            }
        } else {
            NotificationUtil.errorNotification("Da scheint etwas nicht zu funktionieren wie es sollte. Bitte versuchen Sie es erneut oder kontaktieren Sie den Support falls das Problem weiterhin besteht.");
            return false;
        }
    }
}