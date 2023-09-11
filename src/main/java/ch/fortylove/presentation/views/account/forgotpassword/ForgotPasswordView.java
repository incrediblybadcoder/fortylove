package ch.fortylove.presentation.views.account.forgotpassword;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.views.account.forgotpassword.events.ForgotPasswordFormSendEvent;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route(value = ForgotPasswordView.ROUTE)
@PageTitle(ForgotPasswordView.PAGE_TITLE)
@AnonymousAllowed
public class ForgotPasswordView extends VerticalLayout {

    @Nonnull private final UserService userService;
    @Nonnull private final NotificationUtil notificationUtil;

    private Registration registration;

    @Nonnull public static final String ROUTE = "forgotpassword";
    @Nonnull public static final String PAGE_TITLE = "Password Reset";

    @Autowired
    public ForgotPasswordView(@Nonnull final UserService userService,
                              @Nonnull final NotificationUtil notificationUtil,
                              @Nonnull final ForgotPasswordForm forgotPasswordForm) {
        this.userService = userService;
        this.notificationUtil = notificationUtil;

        registration = forgotPasswordForm.addForgotPasswordFormSendEventListener(this::sendPasswordResetEmail);
        setHorizontalComponentAlignment(Alignment.CENTER, forgotPasswordForm);

        add(forgotPasswordForm);
    }

    private void sendPasswordResetEmail(@Nonnull final ForgotPasswordFormSendEvent forgotPasswordFormSendEvent) {
        final String email = forgotPasswordFormSendEvent.getEmail();
        final Optional<User> byEmail = userService.findByEmail(email);
        // Aus Sicherheitsgründen in beiden Fällen die gleiche Meldung ausgeben
        if (byEmail.isEmpty() || !byEmail.get().isEnabled())  {
            notificationUtil.errorNotification("Kein aktiver Benutzer mit dieser E-Mail-Adresse gefunden");
            return;
        }
        userService.generateAndSaveResetToken(email, 2);
        notificationUtil.informationNotification("E-Mail mit Link zum Zurücksetzen des Passworts wurde versendet");
    }

    @Override
    protected void onDetach(final DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        if (registration != null) {
            // Memory leaks verhindern
            registration.remove();
            registration = null;
        }
    }
}
