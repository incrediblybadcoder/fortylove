package ch.fortylove.presentation.views.registration;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.ButtonFactory;
import ch.fortylove.presentation.components.InputFieldFactory;
import ch.fortylove.presentation.fieldvalidators.FirstNameValidator;
import ch.fortylove.presentation.fieldvalidators.LastNameValidator;
import ch.fortylove.presentation.views.registration.events.CancelRegistrationEvent;
import ch.fortylove.presentation.views.registration.events.RegistrationEvent;
import com.vaadin.flow.component.ComponentEventListener;
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
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AnonymousAllowed
public class RegistrationForm extends FormLayout {

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

    public RegistrationForm(@Nonnull final User user) {
        this.user = user;

        binder = new Binder<>(User.class);
        binder.bindInstanceFields(this);

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
        cancel = ButtonFactory.createNeutralButton("Abbrechen", this::cancelClick);
    }

    private void cancelClick() {
        fireEvent(new CancelRegistrationEvent(this));
    }

    @Nonnull
    public Registration addCancelEventListener(@Nonnull final ComponentEventListener<CancelRegistrationEvent> listener) {
        return addListener(CancelRegistrationEvent.class, listener);
    }


    private void registerClick() {
        if (binder.writeBeanIfValid(user)) {
            fireEvent(new RegistrationEvent(this, user, plainPassword.getValue()));
        }
    }

    @Nonnull
    public Registration addRegistrationEventListener(@Nonnull final ComponentEventListener<RegistrationEvent> listener) {
        return addListener(RegistrationEvent.class, listener);
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
