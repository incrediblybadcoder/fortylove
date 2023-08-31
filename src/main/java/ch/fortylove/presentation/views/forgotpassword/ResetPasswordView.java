package ch.fortylove.presentation.views.forgotpassword;

import ch.fortylove.presentation.views.forgotpassword.events.ResetPasswordFormSetPasswordEvent;
import ch.fortylove.presentation.views.login.LoginView;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
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
    @Nonnull private final PasswordEncoder passwordEncoder;
    private Registration registration;
    private String resetToken;

    public ResetPasswordView(@Nonnull final ResetPasswordForm resetPasswordForm,
                             @Nonnull final UserService userService,
                             @Nonnull final PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        registration = resetPasswordForm.addResetPasswordFormSetPasswordEventListener(this::setNewPassword);
        setHorizontalComponentAlignment(Alignment.CENTER, resetPasswordForm);

        add(resetPasswordForm);

        addDetachListener(event -> onDetach());
    }

    private void onDetach() {
        if (registration != null) {
            // Memory leaks verhindern
            registration.remove();
            registration = null;
        }
    }

    private void setNewPassword(ResetPasswordFormSetPasswordEvent resetPasswordFormSetPasswordEvent) {
        if (resetToken == null || resetToken.trim().isEmpty()) {
            NotificationUtil.errorNotification("Ungültiger Token");
            return;
        }
        if (userService.resetPasswordUsingToken(resetToken, passwordEncoder.encode(resetPasswordFormSetPasswordEvent.getPlainPassword()))) {
            NotificationUtil.informationNotification("Neues Passwort wurde gesetzt");
            UI.getCurrent().navigate(LoginView.class);
        } else {
            NotificationUtil.errorNotification("Fehler beim Setzen des neuen Passworts");
        }
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        final List<String> token = event.getLocation().getQueryParameters().getParameters().get("token");

        if (token == null || token.isEmpty()) {
            handleMissingToken();
            return;
        }
        resetToken = token.get(0);
    }

    private void handleMissingToken() {
        NotificationUtil.errorNotification("Token fehlt");
    }
}


