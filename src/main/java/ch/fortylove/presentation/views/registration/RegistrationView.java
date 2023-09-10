package ch.fortylove.presentation.views.registration;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.views.login.LoginView;
import ch.fortylove.presentation.views.registration.events.CancelRegistrationEvent;
import ch.fortylove.presentation.views.registration.events.RegistrationEvent;
import ch.fortylove.service.UserService;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.Nonnull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Route(value = RegistrationView.ROUTE)
@PageTitle(RegistrationView.PAGE_TITLE)
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    @Nonnull public static final String ROUTE = "registration";
    @Nonnull public static final String PAGE_TITLE = "Registration";

    @Nonnull private final UserService userService;
    @Nonnull private final NotificationUtil notificationUtil;
    @Nonnull private final PasswordEncoder passwordEncoder;
    @Nonnull private final List<Registration> registrations;



    public RegistrationView(@Nonnull final RegistrationForm registrationForm,
                            @Nonnull final UserService userService,
                            @Nonnull final NotificationUtil notificationUtil,
                            @Nonnull final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.notificationUtil = notificationUtil;
        this.passwordEncoder = passwordEncoder;

        registrations = new ArrayList<>();
        registrations.add(registrationForm.addRegistrationEventListener(this::registerUser));
        registrations.add(registrationForm.addCancelEventListener(this::onCancel));

        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
        add(registrationForm);
    }

    private void onCancel(CancelRegistrationEvent cancelRegistrationEvent) {
        gotToLoginPage();
    }

    private void registerUser(RegistrationEvent registrationEvent) {
        final User user = registrationEvent.getUser();
        // Todo überprüfen ob der User in der User-Tabel existiert ODER in der neuen UnregisteredUser-Tabel
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                notificationUtil.errorNotification("Es gibt bereits einen Benutzer mit dieser E-Mail-Adresse.");
            } else {
                user.getAuthenticationDetails().setEncryptedPassword(passwordEncoder.encode(registrationEvent.getPlainPassword()));
                // Todo: Hier muss der User in die UnregisteredUser-Tabel geschrieben werden
                userService.create(user, true);
                gotToLoginPage();
                notificationUtil.informationNotification("Überprüfe deine E-Mails um deine Registrierung abzuschliessen.");
        }
    }

    private void gotToLoginPage() {
        UI.getCurrent().navigate(LoginView.ROUTE);
    }

    @Override
    protected void onDetach(final DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        for (Registration registration : registrations) {
            registration.remove();
        }
        registrations.clear();
    }
}
