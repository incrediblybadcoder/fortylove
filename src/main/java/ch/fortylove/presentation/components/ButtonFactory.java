package ch.fortylove.presentation.components;

import ch.fortylove.presentation.views.login.LoginView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Nonnull;

import java.util.function.Consumer;

@SpringComponent
public class ButtonFactory {

    /**
     * Erstellt einen Button für positive Aktionen.
     * Dieser Button-Stil eignet sich für Hauptaktionen wie "Ok", "Registrieren", "Bestätigen" oder "Speichern".
     *
     * @param label Der anzuzeigende Text des Buttons.
     * @return Ein für positive Aktionen stilisierter Button.
     */
    @Nonnull
    public Button createPrimaryButton(String label, Runnable onClickAction) {
        Button button = new Button();
        button.setText(label);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.setWidthFull();
        button.addClickListener(event -> onClickAction.run());
        return button;
    }

    @Nonnull
    public Button createPrimaryButton(String label, Consumer<ClickEvent<Button>> clickListener) {
        Button button = new Button();
        button.setText(label);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.setWidthFull();
        if (clickListener != null) {
            button.addClickListener(clickListener::accept);
        }
        return button;
    }

    /**
     * Erstellt einen Button für neutrale oder rückgängig machende Aktionen.
     * Dieser Button-Stil eignet sich für Aktionen, die den Benutzer zurückführen oder einen Prozess abbrechen,
     * wie z.B. "Abbrechen".
     *
     * @param label Der anzuzeigende Text des Buttons.
     * @return Ein für neutrale Aktionen stilisierter Button.
     */
    @Nonnull
    public Button createNeutralButton(String label, Runnable onClickAction) {
        Button button = new Button();
        button.setText(label);
        button.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        button.setWidthFull();
        button.addClickListener(event -> onClickAction.run());
        return button;
    }

    @Nonnull
    public Button createNeutralButton(String label, Consumer<ClickEvent<Button>> clickListener) {
        Button button = new Button();
        button.setText(label);
        button.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        button.setWidthFull();
        if (clickListener != null) {
            button.addClickListener(clickListener::accept);
        }
        return button;
    }

    /**
     * Erstellt einen Button für destruktive oder Warn-Aktionen.
     * Dieser Button-Stil eignet sich für Aktionen, die potenziell irreversible Auswirkungen haben könnten,
     * wie z.B. "Löschen".
     *
     * @param label Der anzuzeigende Text des Buttons.
     * @return Ein für destruktive Aktionen stilisierter Button.
     */
    @Nonnull
    public Button createDangerButton(String label, Runnable onClickAction) {
        Button button = new Button();
        button.setText(label);
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        button.setWidthFull();
        button.addClickListener(event -> onClickAction.run());
        return button;
    }

    @Nonnull
    public Button createBackToLoginButton() {
        final Button backToLoginButton = new Button("Zurück zur Login Seite", event -> UI.getCurrent().navigate(LoginView.ROUTE));
        backToLoginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return backToLoginButton;
    }
}