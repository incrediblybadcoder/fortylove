package ch.fortylove.presentation.views.account.forgotpassword;

import ch.fortylove.presentation.components.ButtonFactory;
import ch.fortylove.presentation.components.InputFieldFactory;
import ch.fortylove.presentation.views.account.forgotpassword.events.ForgotPasswordFormSendEvent;
import ch.fortylove.presentation.views.account.login.LoginView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ForgotPasswordForm extends FormLayout {

    @Nonnull private final ButtonFactory buttonFactory;
    @Nonnull private final InputFieldFactory inputFieldFactory;

    private TextField email;
    private Button sendButton;
    private final Binder<String> binder;

    public ForgotPasswordForm(@Nonnull final ButtonFactory buttonFactory,
                              @Nonnull final InputFieldFactory inputFieldFactory) {
        this.buttonFactory = buttonFactory;
        this.inputFieldFactory = inputFieldFactory;

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
        final H2 title = new H2("Link zum Zurücksetzen des Passworts anfordern");
        email = inputFieldFactory.createTextField("E-Mail");
        sendButton = buttonFactory.createPrimaryButton("Senden", this::sendPasswordResetEmail);
        sendButton.addClickShortcut(Key.ENTER);
        final Button cancel = buttonFactory.createNeutralButton("Abbrechen", this::gotToLoginPage);
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
        UI.getCurrent().navigate(LoginView.ROUTE);
    }

    private void bindFields() {
        binder.forField(email)
                .withValidator(new EmailValidator("Bitte geben Sie eine gültige E-Mail-Adresse ein"))
                .bind(data -> data, (data, value) -> {
                });

        binder.setBean("");
    }

    private void sendPasswordResetEmail() {
        fireEvent(new ForgotPasswordFormSendEvent(this, email.getValue()));
    }

    @Nonnull
    public Registration addForgotPasswordFormSendEventListener(@Nonnull final ComponentEventListener<ForgotPasswordFormSendEvent> listener) {
        return addListener(ForgotPasswordFormSendEvent.class, listener);
    }
}
