package ch.fortylove.view.membermanagement.events;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.view.membermanagement.UserForm;

public class SaveEvent extends UserFormEvent {
    public SaveEvent(UserForm source, User user) {
        super(source, user);
    }
}
