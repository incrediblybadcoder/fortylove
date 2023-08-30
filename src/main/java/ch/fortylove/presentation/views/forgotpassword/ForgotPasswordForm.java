package ch.fortylove.presentation.views.forgotpassword;

import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import ch.fortylove.util.uielements.ButtonsUtil;
import ch.fortylove.util.uielements.InputFieldsUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AnonymousAllowed
public class ForgotPasswordForm extends FormLayout {

    @Nonnull private final UserService userService;
    private TextField email;
    private Button sendButton;
    private final Binder<String> binder;

    public ForgotPasswordForm(@Nonnull final UserService userService) {
        this.userService = userService;

        this.binder = new Binder<>();
        binder.addValueChangeListener(valueChangeEvent -> updateButtonState());

        constructUI();
        bindFields();

        updateButtonState();
    }

        private void updateButtonState() {
            final boolean valid = binder.isValid();
            sendButton.setEnabled(valid);
        }

    private void constructUI() {
        H2 title = new H2("Link zum Zurücksetzen des Passworts anfordern");
        email = InputFieldsUtil.createTextField("E-Mail");
        sendButton = ButtonsUtil.createPrimaryButton("Senden", this::sendPasswordResetEmail);
        sendButton.addClickShortcut(Key.ENTER);
        Button cancel = ButtonsUtil.createNeutralButton("Abbrechen", this::gotToLoginPage);
        add(title, email, sendButton, cancel);
        setMaxWidth("1000px");
        setResponsiveSteps(new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490", 2, ResponsiveStep.LabelsPosition.TOP));
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(sendButton, 2);
        setColspan(cancel, 2);

    }

    private void gotToLoginPage(final ClickEvent<Button> buttonClickEvent) {
        buttonClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate("login"));
    }

    private void sendPasswordResetEmail() {
        NotificationUtil.informationNotification("Password reset email sent");
        userService.generateAndSaveResetToken(email.getValue());
    }

    private void bindFields() {
        binder.forField(email)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige E-Mail-Adresse ein"))
                .bind(data -> data, (data, value) -> {});

        binder.setBean("");
    }
}
