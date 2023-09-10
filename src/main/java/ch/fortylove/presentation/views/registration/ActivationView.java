package ch.fortylove.presentation.views.registration;

import ch.fortylove.presentation.views.login.LoginView;
import ch.fortylove.service.UnvalidatedUserService;
import ch.fortylove.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

import java.util.List;

@Route("activate")
@AnonymousAllowed
public class ActivationView extends Composite implements BeforeEnterObserver {
    private VerticalLayout layout;

    @Nonnull private final UserService userService;
    @Nonnull private final UnvalidatedUserService unvalidatedUserService;

    public ActivationView(UserService userService,
                          @Nonnull final UnvalidatedUserService unvalidatedUserService) {
        this.userService = userService;
        this.unvalidatedUserService = unvalidatedUserService;
    }

    @Override
    protected Component initContent() {
        layout = new VerticalLayout();
        return layout;
    }

    @Override
    public void beforeEnter(final BeforeEnterEvent event) {
        final List<String> codes = event.getLocation().getQueryParameters().getParameters().get("code");

        if (codes == null || codes.isEmpty()) {
            handleMissingActivationCode();
            return;
        }

        final String activationCode = codes.get(0);

        if (checkActivationStatusOfUnvalidatedUser(activationCode)) {
            handleActivation(activationCode);
        } else {
            handleNotReady();
        }
    }

    private void handleNotReady() {
        layout.add(
                new Text("Konto wurde bereits aktiviert."), // Todo Sicher? Könnte auch sein, dass kein UnvalidatedUser gefunden wurde.
                createLoginLink()
        );
    }

    private void handleActivation(String activationCode) {
        if (unvalidatedUserService.activate(activationCode).isSuccessful()) {
            layout.add(
                    new Text("Konto wurde erfolgreich aktiviert."),
                    createLoginLink()
            );
        } else {
            layout.add(
                    new Text("Die Aktivierung ist fehlgeschlagen. Der Aktivierungscode ist ungültig."),
                    new RouterLink("Registrierung erneut versuchen", RegistrationView.class),
                    new Text("Benötigen Sie Hilfe? Kontaktieren Sie uns unter fortylove.untervaz@gmail.com")
            );
        }
    }

    private void handleMissingActivationCode() {
        String htmlContent =
                "<div>" +
                        "<span>Upps da ist etwas schiefgelaufen. Versuchen Sie es erneut mit demselben Aktivierungslink aus der E-Mail.</span>" +
                        "<br>" +
                        "<span>Benötigen Sie Hilfe? Kontaktieren Sie uns unter fortylove.untervaz@gmail.com</span>" +
                        "</div>";

        Html content = new Html(htmlContent);
        layout.add(content);
    }

    private RouterLink createLoginLink() {
        return new RouterLink("Login", LoginView.class);
    }


    private boolean checkActivationStatusOfUnvalidatedUser(String activationCode) {
        return unvalidatedUserService.checkIfReadyToActivate(activationCode);
    }
}
