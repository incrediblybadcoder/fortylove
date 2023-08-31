package ch.fortylove.presentation.views.forgotpassword.events;

import ch.fortylove.presentation.views.forgotpassword.ForgotPasswordForm;
import com.vaadin.flow.component.ComponentEvent;

public class ForgotPasswordFormSendEvent extends ComponentEvent<ForgotPasswordForm> {
    private final String email;

    public ForgotPasswordFormSendEvent(ForgotPasswordForm source, String email) {
        super(source, false);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

