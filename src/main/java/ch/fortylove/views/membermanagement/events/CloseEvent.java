package ch.fortylove.views.membermanagement.events;

import ch.fortylove.views.membermanagement.UserForm;

public class CloseEvent extends UserFormEvent {
    public CloseEvent(UserForm source) {
        super(source, null);
    }
}
