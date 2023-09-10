package ch.fortylove.presentation.views.management.usermanagement.admissiondialog;

import ch.fortylove.persistence.entity.PlayerStatus;
import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.components.dialog.CancelableDialog;
import ch.fortylove.service.PlayerStatusService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdmissionDialog extends CancelableDialog {

    @Nonnull private final PlayerStatusService playerStatusService;

    @Nonnull private final TextField firstNameField = new TextField("Vorname");
    @Nonnull private final TextField lastNameField = new TextField("Nachname");
    @Nonnull private final TextField emailField = new TextField("E-Mail");
    @Nonnull private final Select<PlayerStatus> playerStatusSelection = new Select<>();
    @Nonnull private final TextArea messageTextArea = new TextArea("Mitteilung");

    private User currentUser;

    @Autowired
    public AdmissionDialog(@Nonnull final PlayerStatusService playerStatusService) {
        this.playerStatusService = playerStatusService;

        constructUI();
    }

    private void constructUI() {
        setHeaderTitle("Aufnahmeanfrage");
        setWidth("300px");

        final VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setSpacing(false);
        dialogLayout.setPadding(false);

        firstNameField.setReadOnly(true);
        firstNameField.setWidthFull();

        lastNameField.setReadOnly(true);
        lastNameField.setWidthFull();

        emailField.setReadOnly(true);
        emailField.setWidthFull();

        playerStatusSelection.setWidthFull();
        playerStatusSelection.setLabel("Spielerstatus");
        playerStatusSelection.setItemLabelGenerator(PlayerStatus::getName);

        messageTextArea.setWidthFull();

        final Button acceptButton = new Button("Annehmen", acceptButtonClickListener());
        acceptButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        final Button rejectButton = new Button("Ablehnen", rejectButtonClickListener());
        rejectButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        final HorizontalLayout buttonContainer = new HorizontalLayout();
        buttonContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        getFooter().add(rejectButton, acceptButton);

        dialogLayout.add(firstNameField, lastNameField, emailField, playerStatusSelection, messageTextArea);
        add(dialogLayout);
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> acceptButtonClickListener() {
        return event -> {
            fireEvent(new AcceptAdmissionDialogEvent(this, currentUser, playerStatusSelection.getValue(), messageTextArea.getValue()));
            close();
        };
    }

    @Nonnull
    private ComponentEventListener<ClickEvent<Button>> rejectButtonClickListener() {
        return event -> {
            fireEvent(new RejectAdmissionDialogEvent(this, currentUser, messageTextArea.getValue()));
            close();
        };
    }

    public void open(@Nonnull final User user) {
        currentUser = user;

        firstNameField.setValue(user.getFirstName());
        lastNameField.setValue(user.getLastName());
        emailField.setValue(user.getEmail());
        playerStatusSelection.setItems(playerStatusService.findAll());
        playerStatusSelection.setValue(playerStatusService.getDefaultMemberPlayerStatus());
        messageTextArea.clear();
        messageTextArea.focus();

        open();
    }

    public void addAcceptAdmissionDialogListener(@Nonnull final ComponentEventListener<AcceptAdmissionDialogEvent> listener) {
        addListener(AcceptAdmissionDialogEvent.class, listener);
    }

    public void addRejectAdmissionDialogListener(@Nonnull final ComponentEventListener<RejectAdmissionDialogEvent> listener) {
        addListener(RejectAdmissionDialogEvent.class, listener);
    }
}
