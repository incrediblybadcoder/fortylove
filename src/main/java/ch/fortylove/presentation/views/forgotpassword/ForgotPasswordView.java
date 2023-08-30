package ch.fortylove.presentation.views.forgotpassword;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

@Route(value = ForgotPasswordView.ROUTE)
@PageTitle(ForgotPasswordView.PAGE_TITLE)
@AnonymousAllowed
public class ForgotPasswordView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "forgotpassword";
    @Nonnull public static final String PAGE_TITLE = "Password Reset";

    public ForgotPasswordView(@Nonnull final ForgotPasswordForm forgotPasswordForm) {
        setHorizontalComponentAlignment(Alignment.CENTER, forgotPasswordForm);

        add(forgotPasswordForm);
    }
}
