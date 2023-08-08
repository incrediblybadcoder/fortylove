package ch.fortylove.util.uielements;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class InputFieldsUtil {
    private static final int MIN_PASSWORD_LENGTH = 8;
    public static TextField createTextField(String label) {
        TextField textField = new TextField(label);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setRequired(true);
        textField.setRequiredIndicatorVisible(true);
        return textField;
    }

    public static TextField createTextField(String label, boolean required) {
        TextField textField = new TextField(label);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setRequired(required);
        textField.setRequiredIndicatorVisible(required);
        return textField;
    }

    public static PasswordField createPasswordField(String label) {
        PasswordField passwordField = new PasswordField(label);
        passwordField.setHelperText("Ein Passwort muss mindestens " + MIN_PASSWORD_LENGTH + " Zeichen lang sein. Es muss mindestens einen Buchstaben und eine Ziffer enthalten.");
        passwordField.setPattern("^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$");
        passwordField.setErrorMessage("Kein gültiges Passwort");
        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
        passwordField.setRequired(true);
        passwordField.setRequiredIndicatorVisible(true);
        return passwordField;
    }

    public static PasswordField createConfirmationPasswordField(String label) {
        PasswordField passwordField = new PasswordField(label);
        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
        passwordField.setRequired(true);
        passwordField.setRequiredIndicatorVisible(true);
        return passwordField;
    }

    /**
     * Erstellt ein neues IntegerField-Objekt mit gegebenen Label, erforderlichen Status sowie minimalen und maximalen Werten.
     *
     * @param label     das Label des Feldes
     * @param required  gibt an, ob das Feld erforderlich ist oder nicht
     * @param min       der minimale zulässige Wert für das Feld
     * @param max       der maximale zulässige Wert für das Feld
     * @return ein projektspezifisches, konfiguriertes IntegerField-Objekt
     */
    public static IntegerField createBasicIntegerField(String label, boolean required, int min, int max) {
        IntegerField integerField = new IntegerField(label);
        integerField.setWidthFull();
        integerField.setStep(1);
        integerField.setStepButtonsVisible(true);
        integerField.setRequired(required);
        integerField.setRequiredIndicatorVisible(required);
        integerField.setMin(min);
        integerField.setMax(max);
        return integerField;
    }

    /**
     * Erstellt ein neues IntegerField-Objekt mit gegebenen Label und erforderlichen Status.
     * Standardmäßig wird kein Minimal- oder Maximalwert festgelegt.
     * Für spezifischere Konfigurationen kann die Methode {@link #createBasicIntegerField(String, boolean, int, int)} verwendet werden.
     *
     * @param label     das Label des Feldes
     * @param required  gibt an, ob das Feld erforderlich ist oder nicht
     * @return ein konfiguriertes IntegerField-Objekt
     */
    public static IntegerField createBasicIntegerField(String label, boolean required) {
        return createBasicIntegerField(label, required, 0, Integer.MAX_VALUE);
    }

    /**
     * Erstellt ein neues IntegerField-Objekt mit gegebenen Label.
     * Das Feld wird als nicht erforderlich betrachtet und es wird kein Minimal- oder Maximalwert festgelegt.
     * Für spezifischere Konfigurationen kann die Methode {@link #createBasicIntegerField(String, boolean, int, int)} verwendet werden.
     *
     * @param label das Label des Feldes
     * @return ein konfiguriertes IntegerField-Objekt
     */
    public static IntegerField createBasicIntegerField(String label) {
        return createBasicIntegerField(label, false);
    }

}
