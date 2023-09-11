package ch.fortylove.presentation.views.account.registration;

import ch.fortylove.presentation.views.account.login.LoginView;
import ch.fortylove.service.UnvalidatedUserService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.Nonnull;

import java.util.List;

@Route(value = ActivationView.ROUTE)
@PageTitle(ActivationView.PAGE_TITLE)
@AnonymousAllowed
public class ActivationView extends Composite<VerticalLayout> implements BeforeEnterObserver {

    @Nonnull public static final String ROUTE = "activate";
    @Nonnull public static final String PAGE_TITLE = "Aktivierung";

    @Nonnull private final UnvalidatedUserService unvalidatedUserService;

    private VerticalLayout layout;

    public ActivationView(@Nonnull final UnvalidatedUserService unvalidatedUserService) {
        this.unvalidatedUserService = unvalidatedUserService;
    }

    @Override
    protected VerticalLayout initContent() {
        layout = new VerticalLayout();
        return layout;
    }

    @Override
    public void beforeEnter(@Nonnull final BeforeEnterEvent event) {
        final List<String> codes = event.getLocation().getQueryParameters().getParameters().get("code");

        if (codes == null || codes.isEmpty()) {
            handleMissingActivationCode();
            return;
        }

        final String activationCode = codes.get(0);

        if (checkActivationStatusOfUnvalidatedUser(activationCode)) {
            // There is a user with the given activation code in the unvalidated users table.
            handleActivation(activationCode);
        } else {
            // There is no user with the given activation code in the unvalidated users table.
            // This means that the user has already been activated.
            // Or there is just no user with the given activation code, due to some error.
            handleNotReady();
        }
    }

    private void handleNotReady() {
        layout.add(
                new Text("Konto wurde bereits aktiviert."),
                createLoginLink()
        );
    }

    private void handleActivation(@Nonnull String activationCode) {
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
        final String htmlContent =
                "<div>" +
                        "<span>Upps da ist etwas schiefgelaufen. Versuchen Sie es erneut mit demselben Aktivierungslink aus der E-Mail.</span>" +
                        "<br>" +
                        "<span>Benötigen Sie Hilfe? Kontaktieren Sie uns unter fortylove.untervaz@gmail.com</span>" +
                        "</div>";

        final Html content = new Html(htmlContent);
        layout.add(content);
    }

    @Nonnull
    private RouterLink createLoginLink() {
        return new RouterLink("Login", LoginView.class);
    }


    private boolean checkActivationStatusOfUnvalidatedUser(@Nonnull final String activationCode) {
        return unvalidatedUserService.checkIfReadyToActivate(activationCode);
    }
}
