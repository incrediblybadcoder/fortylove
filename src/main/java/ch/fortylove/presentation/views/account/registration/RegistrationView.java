package ch.fortylove.presentation.views.account.registration;

import ch.fortylove.persistence.entity.UnvalidatedUser;
import ch.fortylove.presentation.views.account.login.LoginView;
import ch.fortylove.presentation.views.account.registration.events.CancelRegistrationEvent;
import ch.fortylove.presentation.views.account.registration.events.RegistrationEvent;
import ch.fortylove.service.UnvalidatedUserService;
import ch.fortylove.service.UserService;
import ch.fortylove.service.util.DatabaseResult;
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
    @Nonnull private final UnvalidatedUserService unvalidatedUserService;
    @Nonnull private final NotificationUtil notificationUtil;
    @Nonnull private final PasswordEncoder passwordEncoder;
    @Nonnull private final List<Registration> registrations;

    public RegistrationView(@Nonnull final RegistrationForm registrationForm,
                            @Nonnull final UserService userService,
                            @Nonnull final UnvalidatedUserService unvalidatedUserService,
                            @Nonnull final NotificationUtil notificationUtil,
                            @Nonnull final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.unvalidatedUserService = unvalidatedUserService;
        this.notificationUtil = notificationUtil;
        this.passwordEncoder = passwordEncoder;

        registrations = new ArrayList<>();
        registrations.add(registrationForm.addRegistrationEventListener(this::registerUser));
        registrations.add(registrationForm.addCancelEventListener(this::onCancel));

        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
        add(registrationForm);
    }

    private void onCancel(@Nonnull final CancelRegistrationEvent cancelRegistrationEvent) {
        gotToLoginPage();
    }

    private void registerUser(@Nonnull final RegistrationEvent registrationEvent) {
        final UnvalidatedUser unvalidatedUser = registrationEvent.getUnvalidatedUser();
            if (userService.findByEmail(unvalidatedUser.getEmail()).isPresent() || unvalidatedUserService.findByEmail(unvalidatedUser.getEmail()).isPresent()) {
                notificationUtil.errorNotification("Es gibt bereits einen Benutzer mit dieser E-Mail-Adresse.");
            } else {
                unvalidatedUser.setEncryptedPassword(passwordEncoder.encode(registrationEvent.getPlainPassword()));

                final DatabaseResult<UnvalidatedUser> databaseResult = unvalidatedUserService.create(unvalidatedUser, true);
                notificationUtil.databaseNotification(databaseResult, "Überprüfe deine E-Mails um deine Registrierung abzuschliessen.");

                gotToLoginPage();
        }
    }

    private void gotToLoginPage() {
        UI.getCurrent().navigate(LoginView.ROUTE);
    }

    @Override
    protected void onDetach(@Nonnull final DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        for (Registration registration : registrations) {
            registration.remove();
        }
        registrations.clear();
    }
}
