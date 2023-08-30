package ch.fortylove.presentation.views.forgotpassword;

import ch.fortylove.presentation.views.login.LoginView;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import ch.fortylove.util.uielements.ButtonsUtil;
import ch.fortylove.util.uielements.InputFieldsUtil;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AnonymousAllowed
public class ResetPasswordForm extends FormLayout {

    @Nonnull private final UserService userService;
    @Nonnull private final PasswordEncoder passwordEncoder;

    private String resetToken;
    private PasswordField plainPassword;
    private PasswordField confirmPlainPassword;
    private String confirmPlainPasswordInput;
    private Button setNewPassword;
    private final Binder<String> binder;


    public ResetPasswordForm(@Nonnull final UserService userService,
                             @Nonnull final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

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
        plainPassword = InputFieldsUtil.createPasswordField("Passwort");
        plainPassword.addValueChangeListener(event -> binder.validate());
        confirmPlainPassword = InputFieldsUtil.createConfirmationPasswordField("Passwort bestätigen");
        setNewPassword = ButtonsUtil.createPrimaryButton("Ok", this::setNewPassword);
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
        if(resetToken == null || resetToken.trim().isEmpty()) {
            NotificationUtil.errorNotification("Ungültiger Token");
            return;
        }
        if (userService.resetPasswordUsingToken(resetToken, passwordEncoder.encode(plainPassword.getValue()))) {
            NotificationUtil.informationNotification("Neues Passwort wurde gesetzt");
            UI.getCurrent().navigate(LoginView.class);
        } else {
            NotificationUtil.errorNotification("Fehler beim Setzen des neuen Passworts");
        }
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

    public void setToken(final String resetToken) {
        this.resetToken = resetToken;
    }
}

