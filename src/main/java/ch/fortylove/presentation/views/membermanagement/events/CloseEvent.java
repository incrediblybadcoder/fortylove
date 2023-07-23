package ch.fortylove.presentation.views.membermanagement.events;

import ch.fortylove.presentation.views.membermanagement.UserForm;

public class CloseEvent extends UserFormEvent {
    public CloseEvent(UserForm source) {
        super(source, null);
    }
}
