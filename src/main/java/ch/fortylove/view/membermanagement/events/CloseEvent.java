package ch.fortylove.view.membermanagement.events;

import ch.fortylove.view.membermanagement.UserForm;

public class CloseEvent extends UserFormEvent {
    public CloseEvent(UserForm source) {
        super(source, null);
    }
}
