package ch.fortylove.presentation.views.forgotpassword;

import ch.fortylove.presentation.components.ButtonFactory;
import ch.fortylove.presentation.components.InputFieldFactory;
import ch.fortylove.presentation.views.forgotpassword.events.ForgotPasswordFormSendEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AnonymousAllowed
public class ForgotPasswordForm extends FormLayout {
    private TextField email;
    private Button sendButton;
    private final Binder<String> binder;

    public ForgotPasswordForm() {

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
        email = InputFieldFactory.createTextField("E-Mail");
        sendButton = ButtonFactory.createPrimaryButton("Senden", this::sendPasswordResetEmail);
        sendButton.addClickShortcut(Key.ENTER);
        Button cancel = ButtonFactory.createNeutralButton("Abbrechen", this::gotToLoginPage);
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

    private void bindFields() {
        binder.forField(email)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige E-Mail-Adresse ein"))
                .bind(data -> data, (data, value) -> {});

        binder.setBean("");
    }

    private void sendPasswordResetEmail() {
//        final Optional<User> byEmail = userService.findByEmail(email.getValue());
//
//        // Aus Sicherheitsgründen in beiden Fällen die gleiche Meldung ausgeben
//        if (byEmail.isEmpty() || !byEmail.get().isEnabled())  {
//            NotificationUtil.errorNotification("Kein aktiver Benutzer mit dieser E-Mail-Adresse gefunden");
//            return;
//        }
//
//        userService.generateAndSaveResetToken(email.getValue());
//        NotificationUtil.informationNotification("Password reset email sent");

        fireEvent(new ForgotPasswordFormSendEvent(this, email.getValue()));
    }


    public Registration addForgotPasswordFormSendEventListener(ComponentEventListener<ForgotPasswordFormSendEvent> listener) {
        return addListener(ForgotPasswordFormSendEvent.class, listener);
    }

}
