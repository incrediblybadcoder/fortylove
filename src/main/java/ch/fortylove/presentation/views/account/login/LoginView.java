package ch.fortylove.presentation.views.account.login;

import ch.fortylove.FortyloveApplication;
import ch.fortylove.presentation.views.account.forgotpassword.ForgotPasswordView;
import ch.fortylove.presentation.views.account.registration.RegistrationView;
import ch.fortylove.presentation.views.legalnotice.LegalNoticeView;
import ch.fortylove.presentation.views.support.SupportView;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

@Route(LoginView.ROUTE)
@PageTitle(LoginView.PAGE_TITLE)
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    @Nonnull public static final String ROUTE = "login";
    @Nonnull public static final String PAGE_TITLE = "Login";

    @Nonnull private final LoginForm loginForm;

    public LoginView(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm = new LoginForm();
        final LoginI18n loginFormInternational = LoginI18n.createDefault();
        final LoginI18n.Form form = loginFormInternational.getForm();
        form.setUsername("E-Mail");

        loginForm.setI18n(loginFormInternational);
        loginForm.setAction("login");

        loginForm.addForgotPasswordListener(event -> loginForm.getUI().ifPresent(ui -> ui.navigate(ForgotPasswordView.class)));

        final Anchor registrationLink = new Anchor(RegistrationView.ROUTE, "Noch kein Account? Hier registrieren!");

        add(new H1(FortyloveApplication.APP_NAME), loginForm, registrationLink, getFooter());
    }

    @Nonnull
    private HorizontalLayout getFooter() {

        final Anchor supportLink = new Anchor(SupportView.ROUTE, SupportView.PAGE_TITLE);
        supportLink.getElement().getStyle()
                .set("color", "grey")
                .set("font-size", "small");

        final Anchor impressumLink = new Anchor(LegalNoticeView.ROUTE, LegalNoticeView.PAGE_TITLE);
        impressumLink.getElement().getStyle()
                .set("color", "grey")
                .set("font-size", "small");
        final HorizontalLayout footer = new HorizontalLayout(supportLink, impressumLink);
        footer.setJustifyContentMode(JustifyContentMode.CENTER);
        return footer;

    }

    @Override
    public void beforeEnter(@Nonnull final BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}