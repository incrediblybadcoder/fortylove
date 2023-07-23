package ch.fortylove.presentation.views;

import ch.fortylove.persistence.error.DuplicateRecordException;
import ch.fortylove.security.AuthenticationService;
import ch.fortylove.util.NotificationUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import javax.annotation.Nonnull;

@Route("registration")
@PageTitle("Registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout implements BeforeEnterObserver {

    @Nonnull private final AuthenticationService authenticationService;

    public RegistrationView(@Nonnull final AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
        addClassName("registration-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        createUI();
    }

    private void createUI() {
        TextField emailField = new TextField("Email");
        TextField firstNameField = new TextField("Vorname");
        TextField lastNameField = new TextField("Nachname");

        PasswordField passwordField = new PasswordField("Passwort");
        PasswordField confirmPasswordField = new PasswordField("Passwort bestätigen");

        Button registerButton = new Button("Registrieren");
        registerButton.addClickListener(buttonClickEvent -> {
            final boolean register = register(emailField.getValue(), firstNameField.getValue(), lastNameField.getValue(), passwordField.getValue(), confirmPasswordField.getValue());
            if (register){
                buttonClickEvent.getSource().getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });

        add(new H2("Registration"), emailField, firstNameField, lastNameField, passwordField, confirmPasswordField, registerButton);

    }

    private boolean register(String email, String firstName, String lastName, String password, String confirmPassword) {
        if(email.trim().isEmpty()){
            NotificationUtil.errorNotification("Email darf nicht leer sein");
            return false;
        }
        if(firstName.trim().isEmpty()){
            NotificationUtil.errorNotification("Vorname darf nicht leer sein");
            return false;
        }
        if(lastName.trim().isEmpty()){
            NotificationUtil.errorNotification("Nachname darf nicht leer sein");
            return false;
        }
        if(password.trim().isEmpty()){
            NotificationUtil.errorNotification("Passwort darf nicht leer sein");
            return false;
        }
        if(confirmPassword.trim().isEmpty()){
            NotificationUtil.errorNotification("Passwort bestätigen darf nicht leer sein");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            NotificationUtil.errorNotification("Passwörter stimmen nicht überein");
            return false;
        }
        try {
            authenticationService.register(email, firstName, lastName, password);
            NotificationUtil.infoNotification("Registration erfolgreich");
            return true;
        } catch (DuplicateRecordException e) {
            NotificationUtil.errorNotification("Registration fehlgeschlagen: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void beforeEnter(@Nonnull final BeforeEnterEvent beforeEnterEvent) {
        NotificationUtil.infoNotification("Registration");
    }
}