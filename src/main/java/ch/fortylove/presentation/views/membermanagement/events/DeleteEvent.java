package ch.fortylove.presentation.views.membermanagement.events;

import ch.fortylove.persistence.entity.User;
import ch.fortylove.presentation.views.membermanagement.UserForm;

public class DeleteEvent extends UserFormEvent {
    public DeleteEvent(UserForm source, User user) {
        super(source, user);
    }
}
