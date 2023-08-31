package ch.fortylove.presentation.views.forgotpassword;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.views.forgotpassword.events.ForgotPasswordFormSendEvent;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.Nonnull;

import java.util.Optional;

@Route(value = ForgotPasswordView.ROUTE)
@PageTitle(ForgotPasswordView.PAGE_TITLE)
@AnonymousAllowed
public class ForgotPasswordView extends VerticalLayout {
    @Nonnull private final UserService userService;
    private Registration registration;

    @Nonnull public static final String ROUTE = "forgotpassword";
    @Nonnull public static final String PAGE_TITLE = "Password Reset";

    public ForgotPasswordView(@Nonnull final ForgotPasswordForm forgotPasswordForm,
                              @Nonnull final UserService userService) {
        this.userService = userService;

        registration = forgotPasswordForm.addForgotPasswordFormSendEventListener(this::sendPasswordResetEmail);
        setHorizontalComponentAlignment(Alignment.CENTER, forgotPasswordForm);

        add(forgotPasswordForm);
        
        addDetachListener(event -> onDetach());
    }

    private void sendPasswordResetEmail(ForgotPasswordFormSendEvent forgotPasswordFormSendEvent) {
        final String email = forgotPasswordFormSendEvent.getEmail();
        final Optional<User> byEmail = userService.findByEmail(email);
        // Aus Sicherheitsgründen in beiden Fällen die gleiche Meldung ausgeben
        if (byEmail.isEmpty() || !byEmail.get().isEnabled())  {
            NotificationUtil.errorNotification("Kein aktiver Benutzer mit dieser E-Mail-Adresse gefunden");
            return;
        }
        userService.generateAndSaveResetToken(email);
        NotificationUtil.informationNotification("Password reset email sent");
    }

    private void onDetach() {
        if (registration != null) {
            // Memory leaks verhindern
            registration.remove();
            registration = null;
        }
    }
}
