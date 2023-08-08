package ch.fortylove.util.uielements;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

public class ButtonsUtil {
    public static Button registrationButton() {
        Button registrationButton = new Button();
        registrationButton.setText("Registrieren");
        registrationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return registrationButton;
    }

    public static Button cancelButton(String label) {
        Button registrationButton = new Button();
        registrationButton.setText(label);
        registrationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return registrationButton;
    }
}