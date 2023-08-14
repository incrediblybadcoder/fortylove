package ch.fortylove.presentation.views.registration;

import ch.fortylove.presentation.views.login.LoginView;
import ch.fortylove.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

@Route("activate")
@AnonymousAllowed
public class ActivationView extends Composite implements BeforeEnterObserver {
    private VerticalLayout layout;

    @Nonnull private final UserService userService;

    public ActivationView(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Component initContent() {
        layout = new VerticalLayout();
        return layout;
    }

    @Override
    public void beforeEnter(final BeforeEnterEvent event) {
        final String activationCode = event.getLocation().getQueryParameters().getParameters().get("code").get(0);
        if(userService.activate(activationCode)) {
            layout.add(
                    new Text("Konto wurde erfolgreich aktiviert."),
                    new RouterLink("Login", LoginView.class)
            );
        }
        else {
            layout.add(
                    new Text("Die Aktivierung ist fehlgeschlagen. Der Aktivierungscode ist ungültig oder bereits verwendet worden."),
                    new RouterLink("Registrierung erneut versuchen", RegistrationView.class),
                    new Text("Benötigen Sie Hilfe? Kontaktieren Sie uns unter fortylove.untervaz@gmail.com")
            );
        }
    }
}
