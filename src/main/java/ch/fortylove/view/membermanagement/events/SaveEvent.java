package ch.fortylove.view.membermanagement.events;

import ch.fortylove.view.membermanagement.UserForm;
import ch.fortylove.view.membermanagement.dto.UserFormInformations;

public class SaveEvent extends UserFormEvent {
    public SaveEvent(UserForm source, UserFormInformations user) {
        super(source, user);
    }
}
