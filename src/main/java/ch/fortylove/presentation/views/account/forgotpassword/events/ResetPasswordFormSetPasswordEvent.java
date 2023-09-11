package ch.fortylove.presentation.views.account.forgotpassword.events;

import ch.fortylove.presentation.views.account.forgotpassword.ResetPasswordForm;
import com.vaadin.flow.component.ComponentEvent;

public class ResetPasswordFormSetPasswordEvent extends ComponentEvent<ResetPasswordForm> {
    private final String plainPassword;

    public ResetPasswordFormSetPasswordEvent(ResetPasswordForm source, String plainPassword ) {
        super(source, false);
        this.plainPassword = plainPassword;
    }
    public String getPlainPassword() {
        return plainPassword;
    }
}

