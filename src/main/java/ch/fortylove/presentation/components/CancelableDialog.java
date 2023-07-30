package ch.fortylove.presentation.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;

public class CancelableDialog extends Dialog {

    public CancelableDialog() {
        setCloseOnEsc(true);

        constructUI();
    }

    private void constructUI() {
        final Button closeButton = new Button(new Icon("lumo", "cross"), event -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        getHeader().add(closeButton);
    }
}
