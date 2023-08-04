package ch.fortylove.util.uielements;

import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class ButtonsUtil {
    private static final int MIN_PASSWORD_LENGTH = 8;
        public static PasswordField createPasswordField(String label) {
            PasswordField passwordField = new PasswordField(label);
            passwordField.setHelperText("Ein Passwort muss mindestens "+ MIN_PASSWORD_LENGTH +" Zeichen lang sein. Es muss mindestens einen Buchstaben und eine Ziffer enthalten.");
            passwordField.setPattern("^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$");
            passwordField.setErrorMessage("Kein gültiges Passwort");
            passwordField.setValueChangeMode(ValueChangeMode.EAGER);
            passwordField.setRequired(true);
            passwordField.setRequiredIndicatorVisible(true);
            return passwordField;
        }
    }