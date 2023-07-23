package ch.fortylove.presentation.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import javax.annotation.Nonnull;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    @Nonnull private final LoginForm loginForm;

    public LoginView(){
        addClassName("login-presentation");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm = new LoginForm();
        final LoginI18n loginFormInternational = LoginI18n.createDefault();
        final LoginI18n.Form form = loginFormInternational.getForm();
        form.setUsername("Email");

        loginForm.setI18n(loginFormInternational);
        loginForm.setAction("login");

        add(new H1("fortylove"), loginForm);
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