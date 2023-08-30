package ch.fortylove.presentation.views.forgotpassword;

import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

import java.util.List;

@Route(value = ResetPasswordView.ROUTE)
@PageTitle(ResetPasswordView.PAGE_TITLE)
@AnonymousAllowed
public class ResetPasswordView extends VerticalLayout implements BeforeEnterObserver {

    @Nonnull public static final String ROUTE = "resetpassword";
    @Nonnull public static final String PAGE_TITLE = "New Password";

    private final ResetPasswordForm resetPasswordForm;

    public ResetPasswordView(@Nonnull final ResetPasswordForm resetPasswordForm) {
        this.resetPasswordForm = resetPasswordForm;
        setHorizontalComponentAlignment(Alignment.CENTER, this.resetPasswordForm);

        add(this.resetPasswordForm);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        final List<String> token = event.getLocation().getQueryParameters().getParameters().get("token");

        if (token == null || token.isEmpty()) {
            handleMissingToken();
            return;
        }

        final String resetToken = token.get(0);

        // Übergeben Sie den Token an das ResetPasswordForm
        resetPasswordForm.setToken(resetToken);
    }

    private void handleMissingToken() {
        NotificationUtil.errorNotification("Token fehlt");
    }
}


