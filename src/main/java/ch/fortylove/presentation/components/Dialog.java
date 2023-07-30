package ch.fortylove.presentation.components;

public class Dialog extends com.vaadin.flow.component.dialog.Dialog {

    public Dialog() {
        setModal(true);
        setCloseOnOutsideClick(false);
        setCloseOnEsc(false);
    }
}
