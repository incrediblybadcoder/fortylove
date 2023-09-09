package ch.fortylove.presentation.views.management.usermanagement;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.dialog.CancelableDialog;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdmissionRequestDialog extends CancelableDialog {

    @Nonnull private final TextField firstNameField = new TextField("Vorname");
    @Nonnull private final TextField lastNameField = new TextField("Nachname");
    @Nonnull private final TextField emailField = new TextField("E-Mail");
    @Nonnull private final TextArea messageTextArea = new TextArea("Mitteilung");

    private User currentUser;

    public AdmissionRequestDialog() {
        constructUI();
    }

    private void constructUI() {
        setHeaderTitle("Aufnahmegesuch");

        final VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setSpacing(false);
        dialogLayout.setPadding(false);

        final String fieldWidth = "300px";
        firstNameField.setReadOnly(true);
        firstNameField.setWidth(fieldWidth);

        lastNameField.setReadOnly(true);
        lastNameField.setWidth(fieldWidth);

        emailField.setReadOnly(true);
        emailField.setWidth(fieldWidth);

        messageTextArea.setWidth(fieldWidth);

        final Button acceptButton = new Button("Aufnehmen", acceptButtonClickListener());
        acceptButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        final Button rejectButton = new Button("Ablehnen", rejectButtonClickListener());
        rejectButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        final HorizontalLayout buttonContainer = new HorizontalLayout();
        buttonContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        getFooter().add(rejectButton, acceptButton);

        dialogLayout.add(firstNameField, lastNameField, emailField, messageTextArea);
        add(dialogLayout);
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> acceptButtonClickListener() {
        return event -> {
            fireEvent(AdmissionRequestDialogEvent.acceptAdmission(this, currentUser, messageTextArea.getValue()));
            close();
        };
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> rejectButtonClickListener() {
        return event -> {
            fireEvent(AdmissionRequestDialogEvent.rejectAdmission(this, currentUser, messageTextArea.getValue()));
            close();
        };
    }

    public void open(@Nonnull final User user) {
        currentUser = user;

        firstNameField.setValue(user.getFirstName());
        lastNameField.setValue(user.getLastName());
        emailField.setValue(user.getEmail());

        messageTextArea.clear();
        open();
    }

    public void addAdmissionRequestDialogListener(@Nonnull final ComponentEventListener<AdmissionRequestDialogEvent> listener) {
        addListener(AdmissionRequestDialogEvent.class, listener);
    }
}
