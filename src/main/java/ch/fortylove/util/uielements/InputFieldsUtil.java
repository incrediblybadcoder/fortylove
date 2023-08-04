package ch.fortylove.util.uielements;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class InputFieldsUtil {

    public static TextField createTextField(String label) {
        TextField textField = new TextField(label);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setRequired(true);
        textField.setRequiredIndicatorVisible(true);
        return textField;
    }
}
