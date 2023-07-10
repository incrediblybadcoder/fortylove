package ch.fortylove.view.membermanagement.events;

import ch.fortylove.view.membermanagement.UserForm;
import ch.fortylove.view.membermanagement.dto.UserFormInformations;

public class DeleteEvent extends UserFormEvent {
    public DeleteEvent(UserForm source, UserFormInformations user) {
        super(source, user);
    }
}
