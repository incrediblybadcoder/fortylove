package ch.fortylove.presentation.views.forgotpassword;

import ch.fortylove.presentation.components.ButtonFactory;
import ch.fortylove.presentation.components.InputFieldFactory;
import ch.fortylove.presentation.views.forgotpassword.events.ResetPasswordFormSetPasswordEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AnonymousAllowed
public class ResetPasswordForm extends FormLayout {
    private PasswordField plainPassword;
    private PasswordField confirmPlainPassword;
    private String confirmPlainPasswordInput;
    private Button setNewPassword;
    private final Binder<String> binder;


    public ResetPasswordForm() {

        this.binder = new Binder<>();
        binder.addValueChangeListener(valueChangeEvent -> updateButtonState());

        constructUI();
        bindFields();

        updateButtonState();
    }

    private void updateButtonState() {
        final boolean valid = binder.isValid();
        setNewPassword.setEnabled(valid);
    }

    private void constructUI() {
        H2 title = new H2("Neues Passwort setzten");
        plainPassword = InputFieldFactory.createPasswordField("Passwort");
        plainPassword.addValueChangeListener(event -> binder.validate());
        confirmPlainPassword = InputFieldFactory.createConfirmationPasswordField("Passwort bestätigen");
        setNewPassword = ButtonFactory.createPrimaryButton("Ok", this::setNewPassword);
        setNewPassword.addClickShortcut(Key.ENTER);
        add(title, plainPassword, confirmPlainPassword, setNewPassword);
        setMaxWidth("1000px");
        setResponsiveSteps(new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490", 2, ResponsiveStep.LabelsPosition.TOP));
        setColspan(title, 2);
        setColspan(plainPassword, 2);
        setColspan(confirmPlainPassword, 2);
        setColspan(setNewPassword, 2);

    }

    private void setNewPassword() {
        fireEvent(new ResetPasswordFormSetPasswordEvent(this, plainPassword.getValue()));
    }

    public Registration addResetPasswordFormSetPasswordEventListener(final ComponentEventListener<ResetPasswordFormSetPasswordEvent> listener) {
        return addListener(ResetPasswordFormSetPasswordEvent.class, listener);
    }

    private void bindFields() {
        binder.forField(plainPassword)
                .withValidator(password -> password != null && !password.trim().isEmpty(), "Passwort darf nicht leer sein.")
                .bind(password -> password, (password, value) -> {});

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
}

