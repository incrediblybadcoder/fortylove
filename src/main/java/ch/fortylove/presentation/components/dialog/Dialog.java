package ch.fortylove.presentation.components.dialog;

public class Dialog extends com.vaadin.flow.component.dialog.Dialog {

    public Dialog() {
        setModal(true);
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);
    }
}
