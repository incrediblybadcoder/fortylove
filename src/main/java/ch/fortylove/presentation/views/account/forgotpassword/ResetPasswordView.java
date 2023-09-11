package ch.fortylove.presentation.views.account.forgotpassword;

import ch.fortylove.presentation.views.account.forgotpassword.events.ResetPasswordFormSetPasswordEvent;
import ch.fortylove.presentation.views.account.login.LoginView;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.Nonnull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Route(value = ResetPasswordView.ROUTE)
@PageTitle(ResetPasswordView.PAGE_TITLE)
@AnonymousAllowed
public class ResetPasswordView extends VerticalLayout implements BeforeEnterObserver {

    @Nonnull public static final String ROUTE = "resetpassword";
    @Nonnull public static final String PAGE_TITLE = "New Password";

    @Nonnull private final UserService userService;
    @Nonnull private final NotificationUtil notificationUtil;
    @Nonnull private final PasswordEncoder passwordEncoder;

    private Registration registration;
    private String resetToken;

    public ResetPasswordView(@Nonnull final ResetPasswordForm resetPasswordForm,
                             @Nonnull final UserService userService,
                             @Nonnull final NotificationUtil notificationUtil,
                             @Nonnull final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.notificationUtil = notificationUtil;
        this.passwordEncoder = passwordEncoder;
        registration = resetPasswordForm.addResetPasswordFormSetPasswordEventListener(this::setNewPassword);
        setHorizontalComponentAlignment(Alignment.CENTER, resetPasswordForm);

        add(resetPasswordForm);
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

    private void setNewPassword(@Nonnull final ResetPasswordFormSetPasswordEvent resetPasswordFormSetPasswordEvent) {
        if (resetToken == null || resetToken.trim().isEmpty()) {
            notificationUtil.errorNotification("Ungültiger Token");
            return;
        }
        if (userService.resetPasswordUsingToken(resetToken, passwordEncoder.encode(resetPasswordFormSetPasswordEvent.getPlainPassword()))) {
            notificationUtil.informationNotification("Neues Passwort wurde gesetzt");
            UI.getCurrent().navigate(LoginView.class);
        } else {
            notificationUtil.errorNotification("Fehler beim Setzen des neuen Passworts");
        }
    }
    @Override
    public void beforeEnter(@Nonnull final BeforeEnterEvent event) {
        final List<String> token = event.getLocation().getQueryParameters().getParameters().get("token");

        if (token == null || token.isEmpty()) {
            handleMissingToken();
            return;
        }
        resetToken = token.get(0);
    }

    private void handleMissingToken() {
        notificationUtil.errorNotification("Token fehlt");
    }
}


